package com.example.data_classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

public class User {
    public static final String COLLECTION_USER = "users";

    public static final String FIELD_ID = "id_";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";
    public static final String FIELD_CLASSES = "classes";
    public static final String FIELD_USERTYPE = "userType";

    String id_;
    private String username;
    private String firstname;
    private String lastname;
    private List<Class> classes;
    private UserType userType;

    public User(){
        this.id_ = null;
        this.username = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
    }

    public User(String _id, String username, String firstname, String lastname, List<Class> classes, UserType userType){
        this.id_ = _id;
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

    public String getId_() {
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

    public void setId_(String id_){
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
