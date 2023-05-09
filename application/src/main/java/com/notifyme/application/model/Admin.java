package com.notifyme.application.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "USER_ID")
    private Long IID;

    @OneToOne
    @MapsId
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String role;
    @ElementCollection
    @CollectionTable(name = "admin_permissions",
            joinColumns = @JoinColumn(name = "USER_ID"))
    private List<String> permissions;

    public Admin(String role,
                 List<String> permissions) {
        this.role = role;
        this.permissions = permissions;
    }

    public Admin(Long iid) {
        this.IID = iid;
    }

    public Long getIID() {
        return IID;
    }


    public String getRole() {
        return role;
    }

    @ElementCollection
    public List<String> getPermissions() {
        return permissions;
    }

    public void setRole(String role) {
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
