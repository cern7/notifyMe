package com.notifyme.application.model;

import lombok.experimental.Tolerate;

public enum TokenStatus {
    INVALIDTOKEN("TOKEN INVALID"),
    EXPIREDTOKEN("TOKEN EXPIRED"),
    VALIDTOKEN("TOKEN VALID");

    TokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    private final String tokenStatus;

    public String value() {
        return this.tokenStatus;
    }

}
