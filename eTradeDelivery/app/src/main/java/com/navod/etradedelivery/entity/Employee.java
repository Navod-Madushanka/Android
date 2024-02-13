package com.navod.etradedelivery.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private String employeeId;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private boolean status;
    public Employee() {
    }
    public Employee(String employeeId, String username, String email, String mobile, String password, boolean status) {
        this.employeeId = employeeId;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
