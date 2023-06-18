package com.notifyme.application.service;

import com.notifyme.application.dto.BookingRequest;
import com.notifyme.application.dto.BookingResponse;
import com.notifyme.application.model.*;
import com.notifyme.application.repository.BookingRepository;
import com.notifyme.application.repository.CustomerRepository;
import com.notifyme.application.repository.EmployeeRepository;
import com.notifyme.application.repository.ServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Repeatable;
import java.util.Set;

@org.springframework.stereotype.Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository bookingRepository,
                          EmployeeRepository employeeRepository,
                          ServiceRepository serviceRepository,
                          CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewBooking(BookingRequest bookingRequest) {
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
                        booking.getStatus(), booking.getPaymentStatus(), booking.getNotes()));
            } else {
                return ResponseEntity.badRequest().body("Employee doesn't provide this service");
            }
        }

        return ResponseEntity.badRequest().body("Something went wrong");

    }

}
