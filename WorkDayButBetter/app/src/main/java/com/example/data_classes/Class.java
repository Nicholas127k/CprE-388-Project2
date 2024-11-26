package com.example.data_classes;

import com.example.utilities.ClassSectionTimeRange;

import java.util.HashMap;
import java.util.List;

public class Class {
    public static final String FIELD_DEPARTMENT = "department";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";

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
    }

    public Class(String department, long code, String name, String description) {
        this.department = department;
        this.name = name;
        this.description = description;
        this.code = code;
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

}
