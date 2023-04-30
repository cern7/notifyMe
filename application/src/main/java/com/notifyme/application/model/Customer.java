package com.notifyme.application.model;

import javax.persistence.*;

import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "USER_ID")
    private Long IID;

    @OneToOne
    @MapsId
    private User user;
    private String paymentID;
    private String invoice;

    public Customer(String paymentID, String invoice) {
        this.paymentID = paymentID;
        this.invoice = invoice;
    }

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings;


    public Customer() {
    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getInvoice() {
        return invoice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Long getIID() {
        return IID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return IID != null && IID.equals(customer.getIID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
