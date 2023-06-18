package com.notifyme.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PENDING("pending"),
    CAPTURED("captured"),
    DECLINED("declined");

    private final String status;
}
