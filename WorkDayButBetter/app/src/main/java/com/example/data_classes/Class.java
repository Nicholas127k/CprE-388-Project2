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

    private HashMap<Integer, Section> sectionBuckets;

    private List<User> classMembers;

    private List<User> signUpQueue;

    public Class() {
        this.department = null;
        this.code = -1;
        this.name = null;
        this.description = null;
        this.classMembers = new ArrayList<>();
        this.signUpQueue = new ArrayList<>();
    }

    public Class(String department, long code, String name, String description, List<User> classMembers, List<User> signUpQueue) {
        this.department = department;
        this.name = name;
        this.description = description;
        this.code = code;
        this.classMembers = classMembers;
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

    public void setClassMembers(List<User> classMembers) {
        this.classMembers = classMembers;
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
