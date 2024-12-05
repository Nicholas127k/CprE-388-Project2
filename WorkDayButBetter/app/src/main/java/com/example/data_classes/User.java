package com.example.data_classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

public class User {
    public static final String COLLECTION_USER = "users";

    public static final String FIELD_ID = "id_";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";
    public static final String FIELD_CLASSES = "classes";
    public static final String FIELD_USERTYPE = "userType";
    private static final String FIELD_INSTITUTIONID = "institutionId";

    String id_;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private List<Class> classes;
    private UserType userType;
    private int institutionId;

    public User(){
        this.id_ = null;
        this.username = null;
        this.email = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
        this.institutionId = -1;
    }

    public User(String _id, String username, String email, String firstname, String lastname, List<Class> classes, UserType userType, int institutionId){
        this.id_ = _id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classes = classes;
        this.userType = userType;
        this.institutionId = institutionId;
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

    public String getEmail(){
        return this.email;
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

    public int getInstitutionId(){
        return this.institutionId;
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

    public void setEmail(String email){
        this.email = email;
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

    public void setInstitutionId(int institutionId){
        this.institutionId = institutionId;
    }


    public void copy(User user){
        this.id_ = user.getId_();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.classes = user.getClasses();
        this.userType = user.getUserType();
        this.institutionId = user.getInstitutionId();
    }

    public void clear(){
        this.id_ = null;
        this.email = null;
        this.username = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
        this.institutionId = -1;
    }
}
