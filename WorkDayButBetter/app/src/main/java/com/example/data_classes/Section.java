package com.example.data_classes;

import com.example.utilities.ClassSectionTimeRange;

import java.util.List;

public class Section {
    public static final String FIELD_ID = "id_";
    public static final String FIELD_MEMBERS = "members";
    public static final String FIELD_TIME = "time";
    public static final String FIELD_LABEL = "label";
    public static final String FIELD_CLASS = "class_";

    public static final int SUCCESS = 0;

    private int id_;
    private List<User> members;
    private ClassSectionTimeRange time;
    private String label;
    private Class class_;

    public Section(){
        this.id_ = -1;
        this.members = null;
        this.time = null;
        this.label = null;
        this.class_ = null;
    }

    public Section(int id_, List<User> members, ClassSectionTimeRange sectionTimeRange, String label, Class class_){
        this.id_ = id_;
        this.members = members;
        this.time = sectionTimeRange;
        this.label = label;
        this.class_ = class_;
    }

    public int getId_(){
        return this.id_;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public ClassSectionTimeRange getTime(){
        return this.time;
    }

    public String getLabel(){
        return this.label;
    }

    public Class getClass_(){
        return this.class_;
    }

    public void setId_(int id_){
        this.id_ = id_;
    }

    public void setMembers(List<User> members){
        this.members = members;
    }

    public void setTime(ClassSectionTimeRange time){
        this.time = time;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setClass_(Class class_){
        this.class_ = class_;
    }



    public static final int ERROR_USER_ALREADY_EXISTS_IN_SECTION = 1;
    public static final int ERROR_ADDING_USER_TO_MEMBERS_LIST = 2;
    public int addMember(User user){
        for(int i = 0; i < this.members.size(); ++i){
            User member = this.members.get(i);
            if(member.getId_() == user.getId_()){
                return ERROR_USER_ALREADY_EXISTS_IN_SECTION;
            }
        }

        boolean userAdditionStatus = this.members.add(user);
        if(!userAdditionStatus){
            return ERROR_ADDING_USER_TO_MEMBERS_LIST;
        }
        return SUCCESS;
    }

    public static final int ERROR_USER_DOES_NOT_EXIST_IN_MEMBERS_LIST = 1;
    public int removeMember(User user){
        boolean userExists = false;
        int userIndex = 0;
        for(int i = 0; i < this.members.size(); ++i){
            User member = this.members.get(i);
            if(member.getId_() == user.getId_()){
                userExists = true;
                userIndex = i;
                break;
            }
        }

        if(!userExists){
            return ERROR_USER_DOES_NOT_EXIST_IN_MEMBERS_LIST;
        }

        this.members.remove(userIndex);

        return SUCCESS;
    }
}
