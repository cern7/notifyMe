package com.notifyme.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("active"),
    SUSPENDED("suspended"),
    PENDING("pending"),
    LOCKED("locked"),
    INACTIVE("inactive"),
    DELETED("deleted");

    private final String status;
}
