package com.navod.etrade.entity;

import java.util.Date;

public class User {
    private String userId;
    private String username;
    private String email;
    private String address;
    private String mobileNumber;
    private String password;
    private String profilePictureUrl;
    private Date createdAt;
    private boolean status;

    public User() {
    }

    public User(String userId, String username, String email, String address, String mobileNumber, String password, String profilePictureUrl, Date createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
