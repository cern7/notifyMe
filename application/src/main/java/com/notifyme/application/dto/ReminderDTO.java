package com.notifyme.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReminderDTO {
    public final String customerEmail;
    public final String customerName;
    public final String bookingDate;
    public final Long bookingId;
//    public final String employeeEmail;


}
