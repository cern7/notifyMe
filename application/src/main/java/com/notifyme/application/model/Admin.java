package com.notifyme.application.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Admin extends User {

    private List<String> role;
    private List<String> permissions;

    public Admin(String firstName,
                 String lastName,
                 String emailAddress,
                 String phoneNumber,
                 List<String> role,
                 List<String> permissions) {
        super(firstName, lastName, emailAddress, phoneNumber);
        this.role = role;
        this.permissions = permissions;
    }

    public Admin() {

    }

    @ElementCollection
    public List<String> getRole() {
        return role;
    }

    @ElementCollection
    public List<String> getPermissions() {
        return permissions;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
