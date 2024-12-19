package com.example.application_data;

import com.example.data_classes.User;

/**
 * Singleton class for managing user data.
 * This class provides a single point of access to the current user's data
 * throughout the application. It ensures that only one instance of the user
 * data is maintained, preventing data inconsistencies.
 */
public class UserDataSingleton {
    /**
     * The single instance of the user data.
     */
    private static User user = null;
    /**
     * Private constructor to prevent external instantiation.
     */
    private UserDataSingleton(){}
    /**
     * Returns the single instance of the user data.
     *
     * If the instance is not yet created, it creates a new instance of the
     * user class and returns it.
     *
     * @return The single instance of the user data.
     */
    public static User getInstance(){
        if(user == null){
            user = new User();
        }

        return user;
    }
    /**
     * This method allows you to update the user data with a new instance of
     * the user class.
     * @param userData The new user data to set.
     * @return The updated single instance of the user data.
     */
    public static User setInstance(User userData){
        user = userData;
        return user;
    }

}
