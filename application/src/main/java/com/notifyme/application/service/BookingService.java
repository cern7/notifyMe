package com.notifyme.application.service;

import com.notifyme.application.entity.Booking;
import com.notifyme.application.model.BookingModel;
import com.notifyme.application.repository.BookingRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

//    public CollectionModel<BookingModel> getAllBookings() {
//        List<Booking> bookingList = (List<Booking>) bookingRepository.findAll();
//
//        return boo
//    }
//    public Optional<BookingModel> getBookingById(Long IID){
//        return bookingRepository.getBookingByIID(IID)
//                .map(booking -> BookingModelAssembler.toModel(booking));
//    }
}
