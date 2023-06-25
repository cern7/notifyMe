package com.notifyme.application.controller;

import com.notifyme.application.dto.BookingResponse;
import com.notifyme.application.repository.BookingRepository;
import com.notifyme.application.service.BookingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/booking")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/customer/all")
    public ResponseEntity<?> getAllByCustomerId(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

        return bookingService.getAllByCstId(token);
    }
}
