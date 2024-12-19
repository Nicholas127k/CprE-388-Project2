package com.example.data_classes;

import java.io.Serializable;
/**
 *this is a helper class that holds what institution(school) the user is a part of
 *
 */
public class Institution implements Serializable {

    public static final String COLLECTIONS_INSTITUTION = "institutions";

    public static final String FIELD_INSTITUTIONID = "id_";
    public static final String FIELD_INSTITUTIONNAME = "institutionName";
    public static final String FIELD_JOINCODE = "joinCode";

    public static final int DEFAULT_ID = -1;
    /**
     *institution id
     */
    private int id_;
    /**
     *institution name
     */
    private String institutionName;
    /**
     *institution join code
     */
    private String joinCode;
    /**
     *institution
     */
    public Institution(){
        this.id_ = DEFAULT_ID;
        this.institutionName = null;
        this.joinCode = null;
    }
    /**
     *@param _id
     *@param institutionName
     * @param joinCode
     *this creates an Institution Example: Iowa State University for students to join
     */
    public Institution(int _id, String institutionName, String joinCode){
        this.id_ = _id;
        this.institutionName = institutionName;
        this.joinCode = joinCode;
    }
    /**
     *gets institution id
     */
    public int getId_() {
        return this.id_;
    }
    /**
     *gets institution name
     */
    public String getInstitutionName(){
        return this.institutionName;
    }
    /**
     *gets institution join code
     */
    public String getJoinCode(){
        return this.joinCode;
    }
    /**
     *sets institution id
     */
    public void setId_(int id_){
        this.id_ = id_;
    }
    /**
     *sets institution institution name
     */
    public void setInstitutionName(String institutionName){
        this.institutionName = institutionName;
    }
    /**
     *sets institution join code
     */
    public void setJoinCode(String joinCode){
        this.joinCode = joinCode;
    }
    /**
     *copys institution
     * @param institution

     */
    public void copy(Institution institution){
        this.id_ = institution.getId_();
        this.institutionName = institution.getInstitutionName();
        this.joinCode = institution.getJoinCode();
    }
    /**
     *clears institution
     */
    public void clear(){
        this.id_ = DEFAULT_ID;
        this.institutionName = null;
        this.joinCode = null;
    }
}
