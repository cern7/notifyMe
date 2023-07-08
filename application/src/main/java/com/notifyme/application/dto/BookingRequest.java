package com.notifyme.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    public Long customerId;
    public Long employeeId;
    public Long serviceId;
    public Long startDateTime;
    public Long endDateTime;
    public String status;
    public String paymentStatus;
    public String notes;
}
