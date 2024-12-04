package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;

public class SectionFirebaseControllerSingleton{
    private static SectionFirebaseController sectionFirebaseController = null;

    private SectionFirebaseControllerSingleton(){}

    public static SectionFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(sectionFirebaseController == null){
            sectionFirebaseController = new SectionFirebaseController(firebaseFirestore);
        }

        return sectionFirebaseController;
    }
}
