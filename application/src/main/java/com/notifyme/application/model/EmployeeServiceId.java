package com.notifyme.application.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeServiceId implements Serializable {
    // EmployeeServiceId embeddable type contains the
    // employeeId and serviceId properties that built up
    // the compound entity identifier
    // Using an embeddable type is the most common way of
    // mapping a compound identifier when using JPA and Hibernate


    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "SERVICE_ID")
    private Long serviceId;

    public EmployeeServiceId() {
    }

    public EmployeeServiceId(Long employeeId, Long serviceId) {
        this.employeeId = employeeId;
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeServiceId that = (EmployeeServiceId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, serviceId);
    }
}
