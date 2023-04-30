package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue
    private Long IID;
    private String name;
    private String description;
    private String price;
    private String duration;
    private boolean availability;
    private String category;
    private String imageUrl;

    @OneToMany(mappedBy = "service")
    private Set<Booking> bookings;

    @ManyToMany(mappedBy = "providedServices")
    @JsonIgnore
    private Set<Employee> employeesForService;

    // the relationship is mapped as a bidirectional @OneToMany
    // JPA association

    // The mappedBy attribute tells Hibernate that the
    // @ManyToOne side on the EmployeeService entity
    // is in charge of controlling the JPA association
    // Service entity will contain a collection of
    // EmployeeServices

    // Can use CascadeType.ALL since the cascade operation
    // goes to the EmployeeService child entity, and not to
    // the other parent entity. The same is true for the
    // orphanRemoval attribute
    @OneToMany(mappedBy = "service",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<EmployeeService> employees = new HashSet<>();


    public Service(Long IID,
                   String name,
                   String description,
                   String price,
                   String duration,
                   boolean availability,
                   String category,
                   String imageUrl) {
        this.IID = IID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.availability = availability;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Service() {

    }

    public Set<EmployeeService> getEmployees() {

        return employees;
    }

    public Long getIID() {
        return IID;
    }

    public void setIID(Long IID) {
        this.IID = IID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return IID != null && IID.equals(service.getIID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
