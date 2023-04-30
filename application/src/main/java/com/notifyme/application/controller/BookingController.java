package com.notifyme.application.controller;

import com.notifyme.application.model.BookingModel;
import com.notifyme.application.repository.BookingRepository;
import com.notifyme.application.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

//    @GetMapping(path = "/{iid}")
//    public ResponseEntity<BookingModel> getBookingById(@PathVariable("iid") Long iid) {
//        return bookingService.getBookingById(iid)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
