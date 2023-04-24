package com.notifyme.application.model;

import javax.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long IID;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "iid")
    private Service service;


}
