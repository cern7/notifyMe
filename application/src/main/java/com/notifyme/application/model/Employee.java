package com.notifyme.application.model;

import jakarta.persistence.Entity;

@Entity
public class Employee extends User {

    private String department;
    private String jobTitle;
    private String salary;

    public Employee(String firstName,
                    String lastName,
                    String emailAddress,
                    String phoneNumber,
                    String department,
                    String jobTitle,
                    String salary) {
        super(firstName, lastName, emailAddress, phoneNumber);
        this.department = department;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    public Employee() {

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
}
