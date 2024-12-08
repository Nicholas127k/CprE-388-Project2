package com.example.data_classes;

import androidx.annotation.NonNull;

import com.example.utilities.ClassSectionTimeRange;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Class implements Serializable {

    public static final String COLLECTION_CLASS = "classes";

    public static final String FIELD_ID = "id_";
    public static final String FIELD_INSTITUTIONID = "institutionId";
    public static final String FIELD_DEPARTMENT = "department";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SECTIONBUCKETS = "sectionBuckets";
    public static final String FIELD_CLASSMEMBERS = "classMembers";
    public static final String FIELD_SIGNUPQUEUE = "signUpQueue";
    public static final String FIELD_PROFESSORS = "professors";

    public static final int SUCCESS = 0;

    /**
     *
     * The id_ is used to store the id of the class
     *
     */
    private int id_;

    /**
     *
     * The institutionId is used to store the id of the institution that contains
     * the class
     *
     */
    private int institutionId;

    /**
     *
     * The department member is used to store the department of the class.
     * For example, CprE, Com S, Phys, Math
     *
     */
    private String department;

    /**
     *
     * The code member is used to store the class number/code.
     * For example, 3090, 3810, 3880
     *
     */
    private long code;

    /**
     *
     * The name member is used to store the class name.
     * For example, Data Structures and Algorithms, Software Development Practices,
     * Embedded Systems II: Android Development.
     *
     */
    private String name;

    /**
     *
     * The description member is used to store a description of the class.
     *
     */
    private String description;

    /**
     *
     * The sectionBuckets member is used to store the different sections of a class with an associated index/key
     *
     * @note It is important to note that the section buckets member is not able to be modified from within the java code,
     * it can only be modified from the backend function
     *
     */
    private HashMap<String, Section> sectionBuckets;

    /**
     *
     * The class members list is a member that is used to store all of the class members inside of a list.
     * This is useful for general student searches and other non-section related searches.
     *
     * @note Like the sectionBuckets member, the class members list should not be written to from the java code.
     * It should only be modified from the backend functions that put students in a section.
     *
     */
    private List<User> classMembers;

    /**
     *
     * The sign-up queue member is used as a way to store students in an organized list that will be used by the backend functions
     * code to put the students in their respective sections.
     *
     * @note The sign-up queue is the only list of where students should be stored. Just add a student to the list and when the counselor
     * clicks the button to assign students to classes, it will take students from the sign-up queue and put them in their respective class sections
     *
     */
    private List<User> signUpQueue;

    private List<User> professors;

    public Class() {
        this.id_ = -1;
        this.institutionId = -1;
        this.department = null;
        this.code = -1;
        this.name = null;
        this.description = null;
        this.sectionBuckets = new HashMap<>();
        this.classMembers = new ArrayList<>();
        this.signUpQueue = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    public Class(int _id, String department, long code, String name, String description, List<User> signUpQueue, int institutionId, List<User> professors) {
        this.id_ = _id;
        this.institutionId = institutionId;
        this.department = department;
        this.name = name;
        this.description = description;
        this.code = code;
        this.sectionBuckets = new HashMap<>();
        this.classMembers = new ArrayList<>();
        this.signUpQueue = signUpQueue;
        this.professors = professors;
    }

    /**
     *
     *
     *  Getters
     *
     */

    public int getId_(){
        return this.id_;
    }

    public int getInstitutionId(){
        return this.institutionId;
    }

    public String getDepartment(){
        return this.department;
    }

    public long getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public List<User> getClassMembers() {
        return this.classMembers;
    }

    public List<User> getSignUpQueue() {
        return this.signUpQueue;
    }

    public HashMap<String, Section> getSectionBuckets(){
        return this.sectionBuckets;
    }

    public List<User> getProfessors(){
        return this.professors;
    }

    /**
     *
     *
     * Setters
     *
     */

    public void setId_(int _id){
        this.id_ = _id;
    }

    public void setInstitutionId(int _id){
        this.institutionId = _id;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setCode(long code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setSignUpQueue(List<User> queue){
        this.signUpQueue = queue;
    }

    public void setProfessors(List<User> professors){
        this.professors = professors;
    }


    public static final int ERROR_MEMBER_ALREADY_EXISTS_IN_SIGN_UP_QUEUE = 1;
    /**
     *
     * This function adds a member to the return queue
     *
     * @param user The user that you want to add to the signUpQueue
     * @return The status of the function
     *
     * @returnValues 1. SUCCESS
     * @returnValues 2. ERROR_MEMBER_ALREADY_EXISTS_IN_SIGN_UP_QUEUE
     *
     */
    public int addMemberToSignUpQueue(User user){
        boolean memberExists = false;
        for(int i = 0; i < this.signUpQueue.size(); ++i){
            User member = this.signUpQueue.get(i);
            if(Objects.equals(member.getId_(), user.getId_())){
                memberExists = true;
            }
        }

        if(memberExists){
            return ERROR_MEMBER_ALREADY_EXISTS_IN_SIGN_UP_QUEUE;
        }

        this.signUpQueue.add(user);
        return SUCCESS;
    }


    public static final int ERROR_MEMBER_DOES_NOT_EXIST_IN_SIGN_UP_QUEUE = 2;
    /**
     *
     * This function removes a specified member from the return queue
     *
     * @param user The user that you want to remove from the signUpQueue
     * @return The status of the function
     *
     * @returnValues 1. SUCCESS
     * @returnValues 2. ERROR_MEMBER_DOES_NOT_EXIST_IN_SIGN_UP_QUEUE
     *
     */
    public int removeMemberFromSignUpQueue(User user){
        boolean memberExists = false;
        int memberIndex = 0;
        for(int i = 0; i < this.signUpQueue.size(); ++i){
            User member = this.signUpQueue.get(i);
            if(Objects.equals(member.getId_(), user.getId_())){
                memberExists = true;
                memberIndex = i;
            }
        }

        if(!memberExists){
            return ERROR_MEMBER_DOES_NOT_EXIST_IN_SIGN_UP_QUEUE;
        }

        this.signUpQueue.remove(memberIndex);
        return SUCCESS;
    }

    /**
     *
     * This function converts the data of the class to a map
     *
     * @note This function is used in the updateClassInDatabase method
     *
     * @return The data of the class represented as a map
     */
    @Exclude
    public Map<String, Object> getClassDataAsMap(){
        Map<String, Object> classData = new HashMap<>();

        classData.put(FIELD_ID, this.getId_());
        classData.put(FIELD_INSTITUTIONID, this.getInstitutionId());
        classData.put(FIELD_DEPARTMENT, this.getDepartment());
        classData.put(FIELD_CODE, this.getCode());
        classData.put(FIELD_NAME, this.getName());
        classData.put(FIELD_DESCRIPTION, this.getDescription());
        classData.put(FIELD_SECTIONBUCKETS, this.getSectionBuckets());
        classData.put(FIELD_CLASSMEMBERS, this.getClassMembers());
        classData.put(FIELD_SIGNUPQUEUE, this.getSignUpQueue());

        return classData;
    }

    /**
     *
     * This function updates the database with the current class data
     *
     * @param firebaseFirestore The firebase firestore instance
     * @return The task of the database transaction
     */
    public Task<Void> updateClassInDatabase(FirebaseFirestore firebaseFirestore){
        return firebaseFirestore.collection(COLLECTION_CLASS).document(String.valueOf(this.getId_())).set(this);
    }


    public static final int SECTION_ALREADY_IN_CLASS = 1;
    public int addSection(Section section){

        Collection<Section> sectionCollection = this.sectionBuckets.values();
        Iterator<Section> sectionCollectionIterator = sectionCollection.iterator();
        while(sectionCollectionIterator.hasNext()){
            Section currentSection = sectionCollectionIterator.next();
            if(currentSection.getLabel().equals(section.getLabel())){
                return SECTION_ALREADY_IN_CLASS;
            }
        }

        this.sectionBuckets.put(String.valueOf(this.sectionBuckets.keySet().size()), section);
        return SUCCESS;
    }

    public static final int USER_NOT_PROFESSOR = 1;
    public static final int USER_ALREADY_EXISTS = 2;
    public int addProfessor(User professor){
        if(professor.getUserType() != UserType.PROFESSOR){
            return USER_NOT_PROFESSOR;
        }

        for(int i = 0; i < this.professors.size(); ++i){
            if(this.professors.get(i).getId_().equals(professor.getId_())){
                return USER_ALREADY_EXISTS;
            }
        }

        this.professors.add(professor);
        return SUCCESS;
    }
}
