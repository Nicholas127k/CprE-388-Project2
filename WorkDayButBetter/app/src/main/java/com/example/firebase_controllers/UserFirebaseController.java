package com.example.firebase_controllers;

import com.example.data_classes.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserFirebaseController {

    private final FirebaseFirestore firebaseFirestore;

    public UserFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }

    public Task<Void> addUser(User user){
        return firebaseFirestore.collection(User.COLLECTION_USER).document(String.valueOf(user.getId_())).set(user);
    }

    public Task<QuerySnapshot> getUserFromId(int _id){
        return firebaseFirestore.collection(User.COLLECTION_USER).whereEqualTo(User.FIELD_ID, _id).get();
    }

}

