package com.example.data_classes;

import java.io.Serializable;
import java.util.List;

public class ClassSignUpQueueEntry implements Serializable {

    private User student;
    private List<Integer> prioritiesList;

    public ClassSignUpQueueEntry(){
        this.student = null;
        this.prioritiesList = null;
    }

    public ClassSignUpQueueEntry(User student, List<Integer> prioritiesList){
        this.student = student;
        this.prioritiesList = prioritiesList;
    }

    public User getStudent(){
        return this.student;
    }

    public List<Integer> getPrioritiesList(){
        return this.prioritiesList;
    }

    public void setStudent(User student){
        this.student = student;
    }

    public void setPrioritiesList(List<Integer> prioritiesList){
        this.prioritiesList = prioritiesList;
    }

}
