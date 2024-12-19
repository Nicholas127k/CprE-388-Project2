package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;
/**
 * Singleton class for managing the UserFirebaseController.
 * This class provides a single point of access to the
 * userFirebaseController instance, ensuring that only one instance
 * is created and used throughout the application.
 */
public class UserFirebaseControllerSingleton{
    /**
     * The single instance of the UserFirebaseController.
     */
    private static UserFirebaseController userFirebaseController = null;
    /**
     * Private constructor to prevent external instantiation.
     */
    private UserFirebaseControllerSingleton(){}
    /**
     * Returns the single instance of the UserFirebaseController.
     *
     * If the instance is not yet created, it creates a new instance using
     * the provided {@link FirebaseFirestore} instance and returns it.
     *
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     * @return The single instance of the UserFirebaseController.
     */
    public static UserFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(userFirebaseController == null){
            userFirebaseController = new UserFirebaseController(firebaseFirestore);
        }

        return userFirebaseController;
    }

}
