package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;

public class InstitutionFirebaseControllerSingleton {

    private static InstitutionFirebaseController institutionFirebaseController = null;

    private InstitutionFirebaseControllerSingleton(){}

    public static InstitutionFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(institutionFirebaseController == null){
            institutionFirebaseController = new InstitutionFirebaseController(firebaseFirestore);
        }

        return institutionFirebaseController;
    }
}
