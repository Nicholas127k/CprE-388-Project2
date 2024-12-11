package com.example.firebase_controllers;

import com.example.data_classes.Institution;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
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

    public Task<QuerySnapshot> getUserFromId(String _id){
        return firebaseFirestore.collection(User.COLLECTION_USER).whereEqualTo(User.FIELD_ID, _id).get();
    }

    public Task<Void> joinInstitution(User user, Institution institution){
        User newInstitutionUser = user.duplicate();
        newInstitutionUser.setInstitutionId(institution.getId_());
        return this.firebaseFirestore.collection(User.COLLECTION_USER).document(String.valueOf(newInstitutionUser.getId_())).set(newInstitutionUser);
    }

    public Task<QuerySnapshot> getAllProfessorsFromInstitutionId(int institutionId){
        return firebaseFirestore.collection(User.COLLECTION_USER).whereEqualTo(User.FIELD_USERTYPE, UserType.PROFESSOR).whereEqualTo(User.FIELD_INSTITUTIONID, institutionId).get();
    }

}

