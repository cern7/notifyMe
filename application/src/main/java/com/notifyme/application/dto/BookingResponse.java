package com.notifyme.application.dto;

import com.notifyme.application.model.BookingStatus;
import com.notifyme.application.model.Employee;
import com.notifyme.application.model.PaymentStatus;
import com.notifyme.application.model.Service;
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
    private Long employeeId;
    private Long customerId;
    private Service service;
}
