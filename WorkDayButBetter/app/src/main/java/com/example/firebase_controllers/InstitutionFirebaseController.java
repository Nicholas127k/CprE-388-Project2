package com.example.firebase_controllers;

import com.example.data_classes.Institution;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InstitutionFirebaseController {

    private FirebaseFirestore firebaseFirestore;

    public InstitutionFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }

    public Task<QuerySnapshot> getInstitutionFromId(int id){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_INSTITUTIONID, id).get();
    }

    public Task<Void> createInstitution(Institution institution){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).document(String.valueOf(institution.getId_())).set(institution);
    }

    public Task<QuerySnapshot> verifyInstitutionExists(Institution institution){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_INSTITUTIONID, institution.getId_()).whereEqualTo(Institution.FIELD_JOINCODE, institution.getJoinCode()).get();
    }

    public Task<QuerySnapshot> checkJoinCode(String joinCode){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_JOINCODE, joinCode).get();
    }

}
