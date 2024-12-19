package com.example.data_classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user in the application.
 * This class encapsulates the data and functionality associated with a user,
 * including their personal information, classes, user type, and institution
 * affiliation. It provides methods for creating, updating, and managing user
 * data.
 */
public class User implements Serializable {
    public static final String COLLECTION_USER = "users";

    public static final String FIELD_ID = "id_";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";
    public static final String FIELD_CLASSES = "classes";
    public static final String FIELD_USERTYPE = "userType";
    public static final String FIELD_INSTITUTIONID = "institutionId";
    public static final String FIELD_PENDINGCLASSES = "pendingClasses";
    public static final String FIELD_COMPLETEDCOURSES = "completedCourses";
    /**
     * These Strings and lists are what information gets stored about the user
     */
    String id_;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private List<Class> classes;
    private List<Integer> pendingClasses;
    private UserType userType;
    private int institutionId;
    private List<Class> completedCourses;
    /**
     * Creates a new, empty user object.
     */
    public User(){
        this.id_ = null;
        this.username = null;
        this.email = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
        this.institutionId = -1;
        this.pendingClasses = null;
        this.completedCourses = null;
    }
    /**
     *
     *creates user with id, username, email, firstname, lastname, class list, usertype(student, profs, counselor), school id, classes student is queued to
     */
    public User(String _id, String username, String email, String firstname, String lastname, List<Class> classes, UserType userType, int institutionId, List<Integer> pendingClasses){
        this.id_ = _id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classes = classes;
        this.userType = userType;
        this.institutionId = institutionId;
        this.pendingClasses = pendingClasses;
        this.completedCourses = new ArrayList<>();
    }
    /**
     * Creates a new user object with the specified details.
     *
     * @param _id             The user's ID.
     * @param username        The user's username.
     * @param email           The user's email.
     * @param firstname       The user's first name.
     * @param lastname        The user's last name.
     * @param classes         The list of classes the user is enrolled in.
     * @param userType        The user's type (e.g., student, professor, counselor).
     * @param institutionId   The ID of the user's institution.
     * @param pendingClasses  The list of classes the user is pending enrollment in.
     *
     */
    public User(String _id, String username, String email, String firstname, String lastname, List<Class> classes, UserType userType, int institutionId, List<Integer> pendingClasses, List<Class> completedCourses){
        this.id_ = _id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classes = classes;
        this.userType = userType;
        this.institutionId = institutionId;
        this.pendingClasses = pendingClasses;
        this.completedCourses = completedCourses;
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

    public List<Integer> getPendingClasses() {
        return this.pendingClasses;
    }

    public List<Class> getCompletedCourses(){
        return this.completedCourses;
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
    public void setPendingClasses(List<Integer> pendingClasses){
        this.pendingClasses = pendingClasses;
    }
    /**
     * add pending class
     */
    public void addPendingClass(Class pendingClass){
        this.pendingClasses.add(pendingClass.getId_());
    }
    /**
     *removes pending class
     */
    public void removePendingClass(Class removeClass){
        for(int i = 0; i < this.pendingClasses.size(); ++i){
            if(removeClass.getId_() == this.pendingClasses.get(i)){
                this.pendingClasses.remove(i);
                return;
            }
        }
    }

    public void setCompletedCourses(List<Class> completedCourses){
        this.completedCourses = completedCourses;
    }

    /**
     * gets the whole user information
     */
    public void copy(User user){
        this.id_ = user.getId_();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.classes = user.getClasses();
        this.userType = user.getUserType();
        this.institutionId = user.getInstitutionId();
        this.pendingClasses = user.getPendingClasses();
        this.completedCourses = user.getCompletedCourses();
    }
    /**
     *sets user info to null
     */
    public void clear(){
        this.id_ = null;
        this.email = null;
        this.username = null;
        this.firstname = null;
        this.lastname = null;
        this.classes = null;
        this.userType = null;
        this.institutionId = -1;
        this.pendingClasses = null;
        this.completedCourses = null;
    }
    /**
     * doubles user information
     */
    public User duplicate(){
        return new User(
                this.id_,
                this.username,
                this.email,
                this.firstname,
                this.lastname,
                this.classes,
                this.userType,
                this.institutionId,
                this.pendingClasses,
                this.completedCourses
        );
    }
    /**
     *fire base again :)
     */
    public Task<Void> updateUserInDatabase(FirebaseFirestore firebaseFirestore){
        return firebaseFirestore.collection(COLLECTION_USER).document(this.id_).set(this);
    }
}
