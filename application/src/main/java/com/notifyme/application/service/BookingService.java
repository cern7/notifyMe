package com.notifyme.application.service;

import com.notifyme.application.dto.BookingRequest;
import com.notifyme.application.dto.BookingResponse;
import com.notifyme.application.dto.ReminderDTO;
//import com.notifyme.application.events.GenericEvent;
//import com.notifyme.application.events.reminder.listener.ReminderEvent;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.model.*;
import com.notifyme.application.repository.BookingRepository;
import com.notifyme.application.repository.CustomerRepository;
import com.notifyme.application.repository.EmployeeRepository;
import com.notifyme.application.repository.ServiceRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@org.springframework.stereotype.Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private final JWTService jwtService;
    private final ApplicationEventPublisher eventPublisher;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository,
                          EmployeeRepository employeeRepository,
                          ServiceRepository serviceRepository,
                          CustomerRepository customerRepository,
                          JWTService jwtService,
                          ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ResponseEntity<?> addNewBooking(@NotNull BookingRequest bookingRequest) {
        Employee employee = employeeRepository
                .findById(bookingRequest.getEmployeeId())
                .orElse(null);

        Set<Service> providedServices = null;
        Service bookedService = serviceRepository
                .findById(bookingRequest.getServiceId()).orElse(null);

        Booking booking = null;

        Customer customer = customerRepository
                .findById(bookingRequest.getCustomerId())
                .orElse(null);

        if (employee != null) {
            providedServices = employee.getProvidedServices();
            if (providedServices.contains(bookedService)) {
                booking = new Booking(customer, employee, bookedService,
                        bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime(),
                        BookingStatus.valueOf(bookingRequest.getStatus()),
                        PaymentStatus.valueOf(bookingRequest.getPaymentStatus()),
                        bookingRequest.notes);
                bookingRepository.save(booking);

                booking = bookingRepository
                        .findByCustomerAndService(customer, bookedService)
                        .orElse(null);

                return ResponseEntity.ok().body(new BookingResponse(booking.getIID(),
                        booking.getStartDateTime().toString(), booking.getEndDateTime().toString(),
                        booking.getStatus(), booking.getPaymentStatus(), booking.getNotes(),
                        booking.getEmployee().getIID(), customer.getIID(),
                        booking.getService()));
            } else {
                return ResponseEntity.badRequest().body("Employee doesn't provide this service");
            }
        }

        return ResponseEntity.badRequest().body("Something went wrong");

    }

    public ResponseEntity<?> getAllByCstId(String token) {
        Long customerId = jwtService.getUserIdFromToken(token);
        if (customerId == null) {
            return ResponseEntity.badRequest().body("Invalid customerId");
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            return ResponseEntity.badRequest().body("Invalid customerId");
        }

        List<Booking> customerBookings = bookingRepository.getAllByCustomer(customer);
        List<BookingResponse> responseBookings = new ArrayList<>(customerBookings.size());
        customerBookings.forEach(booking -> {
            responseBookings.add(new BookingResponse(booking.getIID(), booking.getStartDateTime().toString(),
                    booking.getEndDateTime().toString(), booking.getStatus(), booking.getPaymentStatus(),
                    booking.getNotes(),
                    booking.getEmployee().getIID(), customerId,
                    booking.getService()));
        });

        return ResponseEntity.ok(responseBookings);
    }

    public ResponseEntity<?> getAllBookings() {
        String today = String.valueOf(System.currentTimeMillis());
        List<Booking> allBookings = bookingRepository.getAllByStartDateTimeAfter(today);
        return ResponseEntity.ok(allBookings);
    }

    private List<Booking> getAllBookingsBetween(Long startDate, Long endDate) {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }

        if (startDate >= endDate) {
            throw new IllegalArgumentException("Start date must be earlier than end date");
        }

        return bookingRepository.getAllBetweenStartAndEndDate(startDate, endDate)
                .orElse(null);
    }

    public List<Booking> getAllBookingsToRemind() {

        long today = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;

        long tomorrow = LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC) * 1000;

        return getAllBookingsBetween(today, tomorrow);
    }


    @Transactional
    public void updateBookingStatus(final Long bookingId, final boolean isNotified) {
        if (bookingRepository.findById(bookingId).isPresent()) {
            bookingRepository.updateNotifiedByBookingIID(bookingId, isNotified);
            LOGGER.info("++++++++Booking notified, flag updated++++++++");
        } else {
            LOGGER.warn("There is no booking with id: {}", bookingId);
        }

    }

    private boolean publishEvent(Booking booking) {
        Date startDate = new Date(booking.getStartDateTime());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String formattedStartDate = dateFormat.format(startDate);

        try {
            ReminderDTO reminderDTO = new ReminderDTO(
                    booking.getCustomer().getUser().getEmailAddress(),
                    booking.getCustomer().getUser().getFirstName(),
                    formattedStartDate,
                    booking.getIID());
            GenericEvent<ReminderDTO> reminderEvent =
                    new GenericEvent<>(reminderDTO);

            eventPublisher.publishEvent(reminderEvent);

            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Couldn't send a reminder email");
        }
    }

    // "0 25 8 * * *"
    // 2023-07-08T08:25
    @Scheduled(cron = "0 49 18 * * *")
    public void handleBookingsReminder() {
        LOGGER.warn("Scheduled job start process.....");
        List<Booking> bookingsToRemind = getAllBookingsToRemind();
        if (bookingsToRemind.isEmpty()) {
            LOGGER.warn("No bookings to remind");
        }
        bookingsToRemind.forEach(booking -> {
            publishEvent(booking);
            LOGGER.warn("Reminder sent for booking: {}", booking.getIID());
        });
    }


}
