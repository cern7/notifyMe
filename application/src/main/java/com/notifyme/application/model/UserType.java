package com.notifyme.application.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType {
    ADMIN("admin"),
    CUSTOMER("customer"),
    EMPLOYEE("employee");

    private final String type;
}
