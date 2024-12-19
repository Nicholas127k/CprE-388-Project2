package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;
/**
 * Singleton class for managing the SectionFirebaseController.
 * This class provides a single point of access to the
 * SectionFirebaseController} instance, ensuring that only one
 * instance is created and used throughout the application.
 */
public class SectionFirebaseControllerSingleton{
    private static SectionFirebaseController sectionFirebaseController = null;
    /**
     * Private constructor to prevent external instantiation.
     * Not used idk
     */
    private SectionFirebaseControllerSingleton(){}
    /**
     * Returns the single instance of the SectionFirebaseController.
     * If the instance is not yet created, it creates a new instance using
     * the provided FirebaseFirestore} instance and returns it.
     *
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     * @return The single instance of the SectionFirebaseController.
     */
    public static SectionFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(sectionFirebaseController == null){
            sectionFirebaseController = new SectionFirebaseController(firebaseFirestore);
        }

        return sectionFirebaseController;
    }
}
