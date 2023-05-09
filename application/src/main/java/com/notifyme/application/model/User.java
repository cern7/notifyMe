package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "userTbl", uniqueConstraints = {@UniqueConstraint(columnNames = "emailAddress")})
public class User {
    /*
     * https://www.baeldung.com/hibernate-inheritance
     */

    // add toString()!!!!!!!!

    // admin, customer,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long IID;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String emailAddress;
    @NotNull(message = "phoneNumber cannot be null")
    @NotBlank(message = "phonenumber cannot be  empty")
//    @Pattern(regexp = "^\\+40-7[0-9]{2}-[0-9]{3}-[0-9]{3}$",
//            message = "Invalid phone number")
    private String phoneNumber;

    //    @NotNull(message = "user status cannot be null")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull(message = "user type cannot be null")
    @Enumerated(EnumType.STRING)
    private UserType type;

    private String geographicAddress;
    private String createDateTime;
    private String lastLoginDateTime;
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Admin admin;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Customer customer;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Employee employee;
//

    public User(Long IID, String firstName, String lastName,
                String emailAddress, String phoneNumber,
                UserStatus status, UserType type,
                String geographicAddress, String createDateTime,
                String lastLoginDateTime, String password) {
        this.IID = IID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.type = type;
        this.geographicAddress = geographicAddress;
        this.createDateTime = createDateTime;
        this.lastLoginDateTime = lastLoginDateTime;
        this.password = password;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return IID != null && IID.equals(user.getIID());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    //https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
}
