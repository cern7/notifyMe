package com.notifyme.application.model;

import jakarta.persistence.Entity;

//@Entity
public class Customer extends User {

    private String paymentID;
    private String invoice;

    public Customer(String firstName, String lastName, String emailAddress, String phoneNumber) {
        super(firstName, lastName, emailAddress, phoneNumber);
    }

    public Customer(String firstName, String lastName, String emailAddress, String phoneNumber, String paymentID, String invoice) {
        super(firstName, lastName, emailAddress, phoneNumber);
        this.paymentID = paymentID;
        this.invoice = invoice;
    }

    public Customer() {

    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
