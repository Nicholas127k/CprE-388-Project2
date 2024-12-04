package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;

public class ClassFirebaseControllerSingleton{
    private static ClassFirebaseController classFirebaseController = null;

    private ClassFirebaseControllerSingleton(){}

    public static ClassFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(classFirebaseController == null){
            classFirebaseController = new ClassFirebaseController(firebaseFirestore);
        }

        return classFirebaseController;
    }
}
