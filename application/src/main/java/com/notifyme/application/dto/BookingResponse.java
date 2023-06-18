package com.notifyme.application.dto;

import com.notifyme.application.model.BookingStatus;
import com.notifyme.application.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingResponse {
    private Long iid;
    private String startDateTime;
    private String endDateTime;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
}
