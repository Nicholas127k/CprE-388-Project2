package com.example.application_data;

import com.example.data_classes.Institution;
/**
 * Singleton class for managing the users institution data.
 * This class provides a single point of access to the current users
 * institution data throughout the application
 */
public class UserInstitutionSingleton {
    /**
     * The single instance of the institution data.
     */
    private static Institution institution = null;
    /**
     * Private constructor to prevent external instantiation.
     */
    private UserInstitutionSingleton() {}
    /**
     * Returns the single instance of the institution data.
     * If the instance is not yet created, it creates a new instance of the
     * institution class and returns it.
     * @return The single instance of the institution data.
     */
    public static Institution getInstance(){

        if(institution == null){
            institution = new Institution();
        }

        return institution;
    }
    /**
     * Sets the single instance of the institution data.
     * This method allows you to update the institution data with a new
     * instance of the institution class.
     * @param institutionData The new institution data to set.
     * @return The updated single instance of the institution data.
     */
    public static Institution setInstance(Institution institutionData){
        institution = institutionData;
        return institution;
    }

}
