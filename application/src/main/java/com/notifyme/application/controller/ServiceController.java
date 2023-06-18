package com.notifyme.application.controller;

import com.notifyme.application.dto.BookingRequest;
import com.notifyme.application.dto.ServiceRequest;
import com.notifyme.application.model.Service;
import com.notifyme.application.service.BookingService;
import com.notifyme.application.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/service")
public class ServiceController {


    private final ServiceService service;
    private final BookingService bookingService;

    public ServiceController(ServiceService service, BookingService bookingService) {
        this.service = service;
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public ResponseEntity<Optional<List<Service>>> getAllAvailableServices() {
        return ResponseEntity.ok().body(service.getAllAvailableServices());
    }

    @PostMapping("/addService")
    public ResponseEntity<?> addService(@RequestBody ServiceRequest serviceRequest) {
        return service.addNewService(serviceRequest);
    }

    @PostMapping("/booking")
    public ResponseEntity<?> bookService(@RequestBody BookingRequest bookingRequest) {
        return bookingService.addNewBooking(bookingRequest);
    }
}
