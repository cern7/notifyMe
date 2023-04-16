package com.notifyme.application.model;

import jakarta.persistence.*;


@Entity
@Table(name="customer")
public class Customer {

    @Id
    @Column(name = "USER_ID")
    private Long IID;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
    private String paymentID;
    private String invoice;

    public Customer(String paymentID, String invoice) {
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
