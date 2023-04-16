package com.notifyme.application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "USER_ID")
    private Long IID;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
    private List<String> role;
    private List<String> permissions;

    public Admin(List<String> role,
                 List<String> permissions) {
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
