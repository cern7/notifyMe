package com.notifyme.application.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

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

    public Long getIID() {
        return IID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return IID != null && IID.equals(admin.getIID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
