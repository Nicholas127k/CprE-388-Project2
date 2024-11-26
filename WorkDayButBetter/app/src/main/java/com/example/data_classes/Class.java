package com.example.data_classes;

import com.example.utilities.ClassSectionTimeRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Class {
    public static final String FIELD_DEPARTMENT = "department";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";

    public static final int SUCCESS = 0;

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
    private HashMap<Integer, Section> sectionBuckets;

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

    public Class() {
        this.department = null;
        this.code = -1;
        this.name = null;
        this.description = null;
        this.sectionBuckets = new HashMap<>();
        this.classMembers = new ArrayList<>();
        this.signUpQueue = new ArrayList<>();
    }

    public Class(String department, long code, String name, String description, List<User> signUpQueue) {
        this.department = department;
        this.name = name;
        this.description = description;
        this.code = code;
        this.sectionBuckets = new HashMap<>();
        this.classMembers = new ArrayList<>();
        this.signUpQueue = signUpQueue;
    }

    /**
     *
     *
     *  Getters
     *
     */

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

    public HashMap<Integer, Section> getSectionBuckets(){
        return this.sectionBuckets;
    }

    /**
     *
     *
     * Setters
     *
     */

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


    public static final int ERROR_MEMBER_ALREADY_EXISTS_IN_SIGN_UP_QUEUE = 1;
    public int addMemberToSignUpQueue(User user){
        boolean memberExists = false;
        for(int i = 0; i < this.signUpQueue.size(); ++i){
            User member = this.signUpQueue.get(i);
            if(member.getId_() == user.getId_()){
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
    public int removeMemberFromSignUpQueue(User user){
        boolean memberExists = false;
        int memberIndex = 0;
        for(int i = 0; i < this.signUpQueue.size(); ++i){
            User member = this.signUpQueue.get(i);
            if(member.getId_() == user.getId_()){
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
}
