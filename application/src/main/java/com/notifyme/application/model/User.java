package com.notifyme.application.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class User implements Serializable {
    /*
     * https://www.baeldung.com/hibernate-inheritance
     */
    @Id
    private Long IID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String status;
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
        this.status = status;
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
        return status;
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
