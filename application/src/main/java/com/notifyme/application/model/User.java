package com.notifyme.application.model;

import com.notifyme.application.validation.UserStatus;
import com.notifyme.application.validation.UserStatusSubset;
import jakarta.persistence.*;

import javax.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    /*
     * https://www.baeldung.com/hibernate-inheritance
     */

   // add toString()!!!!!!!!
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IID")
    private Long IID;

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotNull(message = "emailAddress cannot be null")
    @Email(message = "invalid email address", regexp = ".+[@].+[\\.].+")
    private String emailAddress;
    @NotNull(message = "phoneNumber cannot be null")
    @NotBlank(message = "phonenumber cannot be  empty")
    @Pattern(regexp = "^\\+40-7[0-9]{2}-[0-9]{3}-[0-9]{3}$",
            message = "Invalid phone number")
    private String phoneNumber;

    @NotNull(message = "user status cannot be null")
    @UserStatusSubset(anyOf = {UserStatus.ACTIVE, UserStatus.INACTIVE,
            UserStatus.SUSPENDED, UserStatus.CLOSED})
    private UserStatus status;
    private String geographicAddress;
    private String createDateTime;
    private String lastLoginDateTime;
    private String password;


    public User() {
    }

    public User(String firstName,
                String lastName,
                String emailAddress,
                String phoneNumber,
                String geographicAddress,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.geographicAddress = geographicAddress;
        this.password = password;
    }

    public User(Long IID,
                String firstName,
                String lastName,
                String emailAddress,
                String phoneNumber,
                String geographicAddress,
                String password) {
        this(firstName, lastName, emailAddress, phoneNumber, geographicAddress, password);
        this.IID = IID;
    }

    public void setIID(Long IID) {
        this.IID = IID;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = UserStatus.valueOf(status);
    }

    public void setGeographicAddress(String geographicAddress) {
        this.geographicAddress = geographicAddress;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setLastLoginDateTime(String lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public Long getIID() {
        return IID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status.getValue();
    }

    public String getGeographicAddress() {
        return geographicAddress;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getLastLoginDateTime() {
        return lastLoginDateTime;
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
