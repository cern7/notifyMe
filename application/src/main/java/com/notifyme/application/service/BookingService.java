package com.notifyme.application.service;

import com.notifyme.application.dto.BookingRequest;
import com.notifyme.application.dto.BookingResponse;
import com.notifyme.application.model.*;
import com.notifyme.application.repository.BookingRepository;
import com.notifyme.application.repository.CustomerRepository;
import com.notifyme.application.repository.EmployeeRepository;
import com.notifyme.application.repository.ServiceRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private final JWTService jwtService;

    public BookingService(BookingRepository bookingRepository,
                          EmployeeRepository employeeRepository,
                          ServiceRepository serviceRepository,
                          CustomerRepository customerRepository,
                          JWTService jwtService) {
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
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
                        booking.getStartDateTime(), booking.getEndDateTime(),
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
            responseBookings.add(new BookingResponse(booking.getIID(), booking.getStartDateTime(),
                    booking.getEndDateTime(), booking.getStatus(), booking.getPaymentStatus(),
                    booking.getNotes(),
                    booking.getEmployee().getIID(), customerId,
                    booking.getService()));
        });

        return ResponseEntity.ok(responseBookings);

    }
}
