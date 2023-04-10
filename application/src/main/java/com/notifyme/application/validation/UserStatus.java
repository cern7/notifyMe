package com.notifyme.application.validation;

public enum UserStatus {
    /*
     * Indicates that the user account is currently active
     * and can be used to access the application.
     */
    ACTIVE("active"),

    /*
     * Indicates that the user account is currently inactive
     * and cannot be used to access the application
     * or if the account is pending activation
     */
    INACTIVE("inactive"),
    /*
     * Indicates that the user account has been locked due
     * to too many unsuccessful login attempts or
     * other security reasons. Or it can indicates that the
     * user account has been temporarily suspended,
     * for example due to a violation of the application's
     * terms of service or code of conduct.
     */
    SUSPENDED("suspended"),
    /*
     * Indicates that the user account has been permanently
     * closed and cannot be used to access the application.
     */
    CLOSED("closed");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
