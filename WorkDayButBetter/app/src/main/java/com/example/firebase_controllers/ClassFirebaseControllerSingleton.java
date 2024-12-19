package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;
/**
 * Singleton class for managing the ClassFirebaseController.
 *
 * This class provides a single point of access to the
 * ClassFirebaseController} instance, ensuring that only one
 * instance is created and used throughout the application.

 */
public class ClassFirebaseControllerSingleton{
    /**
     * The single instance of the ClassFirebaseController.
     */
    private static ClassFirebaseController classFirebaseController = null;
    /**
     * Private constructor to prevent external instantiation.
     * Not used
     */
    private ClassFirebaseControllerSingleton(){}
    /**
     * Returns  the single instance of the ClassFirebaseController.
     * If the instance is not yet created, it creates  a new instance using
     * the provided FirebaseFirestore} instance and returns it.
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     * @return The single instance of the ClassFirebaseController.
     */
    public static ClassFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(classFirebaseController == null){
            classFirebaseController = new ClassFirebaseController(firebaseFirestore);
        }

        return classFirebaseController;
    }
}
