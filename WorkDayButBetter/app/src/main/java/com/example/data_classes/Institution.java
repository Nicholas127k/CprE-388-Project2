package com.example.data_classes;

import java.io.Serializable;

public class Institution implements Serializable {

    public static final String COLLECTIONS_INSTITUTION = "institutions";

    public static final String FIELD_INSTITUTIONID = "id_";
    public static final String FIELD_INSTITUTIONNAME = "institutionName";
    public static final String FIELD_JOINCODE = "joinCode";

    public static final int DEFAULT_ID = -1;

    private int id_;
    private String institutionName;
    private String joinCode;

    public Institution(){
        this.id_ = DEFAULT_ID;
        this.institutionName = null;
        this.joinCode = null;
    }

    public Institution(int _id, String institutionName, String joinCode){
        this.id_ = _id;
        this.institutionName = institutionName;
        this.joinCode = joinCode;
    }

    public int getId_() {
        return this.id_;
    }

    public String getInstitutionName(){
        return this.institutionName;
    }

    public String getJoinCode(){
        return this.joinCode;
    }

    public void setId_(int id_){
        this.id_ = id_;
    }

    public void setInstitutionName(String institutionName){
        this.institutionName = institutionName;
    }

    public void setJoinCode(String joinCode){
        this.joinCode = joinCode;
    }

    public void copy(Institution institution){
        this.id_ = institution.getId_();
        this.institutionName = institution.getInstitutionName();
        this.joinCode = institution.getJoinCode();
    }

    public void clear(){
        this.id_ = DEFAULT_ID;
        this.institutionName = null;
        this.joinCode = null;
    }
}
