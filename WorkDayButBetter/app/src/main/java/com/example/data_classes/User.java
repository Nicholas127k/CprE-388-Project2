package com.example.data_classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

public class User {

    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";
    public static final String FIELD_CLASSES = "classes";
    public static final String FIELD_USERTYPE = "userType";

    int id_;
    private String username;
    private String firstname;
    private String lastname;
    private List<Class> classes;
    private UserType userType;

    public User(){
        this.id_ = -1;
        this.username = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
    }

    public User(String username, String firstname, String lastname, List<Class> classes, UserType userType){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classes = classes;
        this.userType = userType;
    }

    /**
     *
     * Getters
     *
     */

    public int getId_() {
        return this.id_;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public List<Class> getClasses(){
        return this.classes;
    }

    public UserType getUserType() {
        return this.userType;
    }

    /**
     *
     * Setters
     *
     */

    public void setId_(int id_){
        this.id_ = id_;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setClasses(List<Class> classes){
        this.classes = classes;
    }

    public void setUserType(UserType userType){
        this.userType = userType;
    }
}