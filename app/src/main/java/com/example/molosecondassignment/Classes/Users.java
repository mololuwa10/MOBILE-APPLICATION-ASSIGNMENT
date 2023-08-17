package com.example.molosecondassignment.Classes;

import android.graphics.Bitmap;

public class Users {
    private String id;
    private String fullName;
    private String email;
    private String dateRegistered;
    private String dateUpdated;
    private String password;
    private String hobbies;
    private String postCode;
    private String userType;
    private String address;
    private String userNumber;
    private Bitmap userImage;

    // Constructor for registration of users
    public Users(String fullName, String email, String dateRegistered, String password, String postCode, String address) {
        this.fullName = fullName;
        this.email = email;
        this.dateRegistered = dateRegistered;
        this.password = password;
        this.postCode = postCode;
        this.address = address;
    }

    public Users(String userID, String userFullName, String userEmail, String dateRegistered,
                 String dateUpdated, String userPassword, String userHobbies, String userPostcode,
                 String userAddress, String userType, String userNumber, Bitmap userImage) {
        this.id = userID;
        this.fullName = userFullName;
        this.email = userEmail;
        this.dateRegistered = dateRegistered;
        this.dateUpdated = dateUpdated;
        this.password = userPassword;
        this.hobbies = userHobbies;
        this.postCode = userPostcode;
        this.address = userAddress;
        this.userType = userType;
        this.userNumber = userNumber;
        this.userImage = userImage;
    }

    // Getters and setters for the Users class

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }
}
