package com.example.firebase_controllers;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserFirebaseControllerSingleton{

    private static UserFirebaseController userFirebaseController = null;

    private UserFirebaseControllerSingleton(){}

    public static UserFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(userFirebaseController == null){
            userFirebaseController = new UserFirebaseController(firebaseFirestore);
        }

        return userFirebaseController;
    }

}
