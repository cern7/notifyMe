package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue
    private Long IID;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String startDateTime;
    private String endDateTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String notes;

    public Booking(Customer customer, Employee employee, Service service,
                   String startDateTime, String endDateTime, BookingStatus status,
                   PaymentStatus paymentStatus, String notes) {
        this.customer = customer;
        this.employee = employee;
        this.service = service;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
    }


}
