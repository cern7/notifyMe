package com.notifyme.application.model;

import com.notifyme.application.validation.UserStatus;
import com.notifyme.application.validation.UserStatusSubset;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@MappedSuperclass
public abstract class User implements Serializable {
    /*
     * https://www.baeldung.com/hibernate-inheritance
     */

    @Id
    private Long IID;

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotNull(message = "emailAddress cannot be null")
    @Email(regexp = ".+[@].+[\\.].+")
    private String emailAddress;
    @NotNull(message = "phoneNumber cannot be null")
    @NotBlank(message = "phonenumber cannot be  empty")
    @Pattern(regexp = "^\\+40-7[0-9]{2}-[0-9]{3}-[0-9]{3}$",
            message = "Invalid phone number")
    private String phoneNumber;

    @NotNull(message = "user status cannot be null")
    @UserStatusSubset(anyOf = {UserStatus.ACTIVE, UserStatus.INACTIVE,
            UserStatus.SUSPENDED, UserStatus.CLOSED})
    private UserStatus status;
    private String geographicAddress;
    private String createDateTime;
    private String lastLoginDateTime;


    public User() {
    }

    public User(String firstName,
                String lastName,
                String emailAddress,
                String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = UserStatus.valueOf(status);
    }

    public void setGeographicAddress(String geographicAddress) {
        this.geographicAddress = geographicAddress;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setLastLoginDateTime(String lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public Long getIID() {
        return IID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status.getValue();
    }

    public String getGeographicAddress() {
        return geographicAddress;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getLastLoginDateTime() {
        return lastLoginDateTime;
    }
}
