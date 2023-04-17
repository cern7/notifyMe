package com.notifyme.application.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employee_service")
public class EmployeeService {

    @EmbeddedId
    private EmployeeServiceId id;

    // The @ManyToOne associations use the @MapsId JPA
    // annotation which tells Hibernate that the underlying FK column
    // is controlled by the EmployeeService entity identifier
    //
    // The Employee property is controlled by the employeeId property
    // in EmployeeServiceId embedded identifier
    // The Service property is controlled by the serviceId property in
    // EmployeeServiceId
    @ManyToOne
    @MapsId("EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @MapsId("SERVICE_ID")
    private Service service;


    public EmployeeService(EmployeeServiceId id, Employee employee, Service service) {
        this.id = id;
        this.employee = employee;
        this.service = service;
    }

    public EmployeeService() {

    }

    public EmployeeServiceId getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeService that = (EmployeeService) o;
        return Objects.equals(id, that.id);
//                Objects.equals(employee, that.employee) &&
//                Objects.equals(service, that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, service);
    }
}
