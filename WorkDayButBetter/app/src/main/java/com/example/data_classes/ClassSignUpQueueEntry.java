package com.example.data_classes;

import java.io.Serializable;
import java.util.List;
/**
 *this is a helper class that creates a sign up queue for classes
 */
public class ClassSignUpQueueEntry implements Serializable {
    /**
     *students get put in the class queue
     */
    private User student;
    /**
     *this would determine where you are in the list
     */
    private List<Integer> prioritiesList;
    /**
     *this creates the queue for classes
     */
    public ClassSignUpQueueEntry(){
        this.student = null;
        this.prioritiesList = null;
    }
    /**
     *this adds students to the que
     *@param student
     *@param prioritiesList
     */
    public ClassSignUpQueueEntry(User student, List<Integer> prioritiesList){
        this.student = student;
        this.prioritiesList = prioritiesList;
    }
    /**
     *gets student from queue
     */
    public User getStudent(){
        return this.student;
    }
    /**
     *gets PrioritiesList
     */
    public List<Integer> getPrioritiesList(){
        return this.prioritiesList;
    }
    /**
     *sets students
     */
    public void setStudent(User student){
        this.student = student;
    }
    /**
     *sets priority list
     */
    public void setPrioritiesList(List<Integer> prioritiesList){
        this.prioritiesList = prioritiesList;
    }

}
