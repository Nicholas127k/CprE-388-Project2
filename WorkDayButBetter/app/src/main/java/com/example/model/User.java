package com.example.model;

public class User {
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_USERTYPE = "userType";
    public static final String FIELD_USERID = "id";
    private String username;
    private String email;
    private int userType; // 0 For student 1 for Advisor
    private int id;

    public User(String username, String email, int userType, int id) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.userType = userType;
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
    public int getUserType() {

        return userType;
    }

    public void setUserType(int userType) {
        /** YOU KNOW WHAT THIS TASTE LIKE PROMOTION **/
        this.userType = userType;
    }
    public int getUserId() {
        return id;
    }

    public void setUserId(String username) {
        this.id = id;
    }
}