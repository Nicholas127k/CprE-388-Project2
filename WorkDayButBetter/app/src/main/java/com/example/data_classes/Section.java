package com.example.data_classes;

import com.example.utilities.ClassSectionTimeRange;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Section {

    public static final String COLLECTION_SECTION = "sections";

    public static final String FIELD_ID = "id_";
    public static final String FIELD_INSTITUTIONID = "institutionId";
    public static final String FIELD_MEMBERS = "members";
    public static final String FIELD_TIME = "time";
    public static final String FIELD_LABEL = "label";
    public static final String FIELD_CLASSID = "classId";

    public static final int SUCCESS = 0;

    private int id_;
    private int institutionId;
    private List<User> members;
    private ClassSectionTimeRange time;
    private String label;
    private int classId;

    public Section(){
        this.id_ = -1;
        this.institutionId = -1;
        this.members = null;
        this.time = null;
        this.label = null;
        this.classId = -1;
    }

    public Section(int id_, List<User> members, ClassSectionTimeRange sectionTimeRange, String label, int class_id, int institutionId){
        this.id_ = id_;
        this.institutionId = institutionId;
        this.members = members;
        this.time = sectionTimeRange;
        this.label = label;
        this.classId = class_id;
    }

    public int getId_(){
        return this.id_;
    }

    public int getInstitutionId(){
        return this.institutionId;
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

    public int getClassId(){
        return this.classId;
    }





    public void setId_(int id_){
        this.id_ = id_;
    }

    public void setInstitutionId(int institutionId){
        this.institutionId = institutionId;
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

    public void setClassId(int class_id){
        this.classId = class_id;
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

    public Map<String, Object> getSectionDataAsMap(){
        Map<String, Object> sectionData = new HashMap<>();

        sectionData.put(FIELD_ID, this.getId_());
        sectionData.put(FIELD_INSTITUTIONID, this.getInstitutionId());
        sectionData.put(FIELD_MEMBERS, this.getMembers());
        sectionData.put(FIELD_TIME, this.getTime());
        sectionData.put(FIELD_LABEL, this.getLabel());
        sectionData.put(FIELD_CLASSID, this.getClassId());

        return sectionData;
    }

    public Task<Void> updateSectionDataInDatabase(FirebaseFirestore firebaseFirestore){
        return firebaseFirestore.collection(COLLECTION_SECTION).document(String.valueOf(this.getId_())).update(this.getSectionDataAsMap());
    }
}
