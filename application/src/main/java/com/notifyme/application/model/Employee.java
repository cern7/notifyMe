package com.notifyme.application.model;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "USER_ID")
    private Long IID;

    // one-to-one relationship with User class
    // using shared primary key or derived identifiers
    // https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#identifiers-derived

    @OneToOne
    @MapsId
    private User user;
    private String department;
    private String jobTitle;
    private String salary;

    @OneToMany(mappedBy = "employee")
    private Set<Booking> bookings;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_to_service",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> providedServices;

    // the relationship is mapped as a bidirectional @OneToMany
    // JPA association

    // The mappedBy attribute tells Hibernate that the
    // @ManyToOne side on the EmployeeService entity
    // is in charge of controlling the JPA association
    // Employee entity will contain a collection of
    // EmployeeServices

    // Can use CascadeType.ALL since the cascade operation
    // goes to the EmployeeService child entity, and not to
    // the other parent entity. The same is true for the
    // orphanRemoval attribute
    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<EmployeeService> services = new HashSet<>();


    public Employee(String department,
                    String jobTitle,
                    String salary) {
        this.department = department;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    public Employee() {

    }

    public Set<EmployeeService> getServices() {
        return services;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Bidirectional @OneToMany
    public void addService(Service service) {
        EmployeeService employeeService = new EmployeeService();
        employeeService.setEmployee(this);
        employeeService.setService(service);
        services.add(employeeService);
        service.getEmployees().add(employeeService);

    }

    // need to synchronize two collections and
    // two @ManyToOne associations
    public void removeService(Service service) {
        for (Iterator<EmployeeService> iterator = services.iterator(); iterator.hasNext(); ) {
            EmployeeService employeeService = iterator.next();
            if (employeeService.getEmployee().equals(this) && employeeService.getService().equals(service)) {
                iterator.remove();
                employeeService.getService().getEmployees().remove(employeeService);
                employeeService.setEmployee(null);
                employeeService.setService(null);
                break;
            }
        }
    }

    public String getDepartment() {
        return department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Long getIID() {
        return IID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return IID != null && IID.equals(employee.getIID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
