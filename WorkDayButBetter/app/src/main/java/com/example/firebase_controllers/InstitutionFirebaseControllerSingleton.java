package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;
/**
 * Singleton class for managing the InstitutionFirebaseController.
 * This class provides a single point of access to the
 * institutionFirebaseController} instance, ensuring that only one
 * instance is created and used throughout the application.
 */
public class InstitutionFirebaseControllerSingleton {
    /**
     * The single instance of the InstitutionFirebaseController.
     */
    private static InstitutionFirebaseController institutionFirebaseController = null;
    /**
     * Private constructor to prevent external instantiation.
     * not used
     */
    private InstitutionFirebaseControllerSingleton(){}
    /**
     * Returns the single instance of the InstitutionFirebaseController.
     * If the instance is not yet created, it creates a new instance using
     * the provided  FirebaseFirestore} instance and returns it.
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     * @return The single instance of the InstitutionFirebaseController.
     */
    public static InstitutionFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(institutionFirebaseController == null){
            institutionFirebaseController = new InstitutionFirebaseController(firebaseFirestore);
        }

        return institutionFirebaseController;
    }
}
