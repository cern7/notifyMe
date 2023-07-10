package com.notifyme.application.dto;

import com.notifyme.application.model.Booking;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookingResponseMapper implements Function<Booking, BookingResponse> {

    @Override
    public BookingResponse apply(Booking booking) {
        return new BookingResponse(
                booking.getIID(),
                booking.getStartDateTime().toString(),
                booking.getEndDateTime().toString(),
                booking.getStatus(),
                booking.getPaymentStatus(),
                booking.getNotes(),
                booking.getEmployee().getIID(),
                booking.getCustomer().getIID(),
                booking.getService());
    }
}
