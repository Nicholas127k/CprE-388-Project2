package com.example.firebase_controllers;

import com.example.data_classes.Institution;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InstitutionFirebaseController {
    /**
     * A controller class for managing institution-related operations in Firebase.
     * This class provides methods for interacting with the Firebase Firestore
     * database to perform operations such as retrieving institution information,
     * creating new institutions, and verifying institution existence.
     */
    private FirebaseFirestore firebaseFirestore;
    /**
     * Constructor for the InstitutionFirebaseController.
     *
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     */
    public InstitutionFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }
    /**
     * Retrieves an institution from the Firebase Firestore database based on its ID.
     *
     * @param id The ID of the institution to retrieve.
     * @return A task representing the asynchronous operation.
     */
    public Task<QuerySnapshot> getInstitutionFromId(int id){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_INSTITUTIONID, id).get();
    }
    /**
     * Creates a new institution in the Firebase Firestore database.
     *
     * @param institution The institution object to create.
     * @return A task representing the asynchronous operation.
     */
    public Task<Void> createInstitution(Institution institution){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).document(String.valueOf(institution.getId_())).set(institution);
    }
    /**
     * Verifies if an institution exists in the Firebase Firestore database.
     *
     * @param institution The institution object to verify.
     * @return A task representing the asynchronous operation.
     */
    public Task<QuerySnapshot> verifyInstitutionExists(Institution institution){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_INSTITUTIONID, institution.getId_()).whereEqualTo(Institution.FIELD_JOINCODE, institution.getJoinCode()).get();
    }
    /**
     * Checks if a join code exists in the Firebase Firestore database.
     *
     * @param joinCode The join code to check.
     * @return A task representing the asynchronous operation.
     */
    public Task<QuerySnapshot> checkJoinCode(String joinCode){
        return this.firebaseFirestore.collection(Institution.COLLECTIONS_INSTITUTION).whereEqualTo(Institution.FIELD_JOINCODE, joinCode).get();
    }

}
