package com.notifyme.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userTbl")
public class User {
    /*
     * https://www.baeldung.com/hibernate-inheritance
     */

    // add toString()!!!!!!!!
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long IID;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    @Email
    private String emailAddress;
    //    @NotNull(message = "phoneNumber cannot be null")
//    @NotBlank(message = "phonenumber cannot be  empty")
//    @Pattern(regexp = "^\\+40-7[0-9]{2}-[0-9]{3}-[0-9]{3}$",
//            message = "Invalid phone number")
    @NotNull
    private String phoneNumber;

    //    @NotNull(message = "user status cannot be null")
//    @UserStatusSubset(anyOf = {UserStatus.ACTIVE, UserStatus.INACTIVE,
//            UserStatus.SUSPENDED, UserStatus.CLOSED})
//    private UserStatus status;
    private String geographicAddress;
    private String createDateTime;
    private String lastLoginDateTime;

    @NotNull
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

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


//    public void setStatus(UserStatus status) {
//        this.status = status;
//    }

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

//    public void setStatus(String status) {
//        this.status = UserStatus.valueOf(status);
//    }

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

//    public String getStatus() {
//        return status.getValue();
//    }

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
