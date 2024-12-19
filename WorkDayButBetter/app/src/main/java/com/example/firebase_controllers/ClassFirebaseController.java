package com.example.firebase_controllers;

import com.example.data_classes.Class;
import com.example.data_classes.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
/**
 * A controller class for managing class  related operations in Firebase.
 *
 * This class provides methods for interacting with the Firebase Firestore
 * database to perform operations such as retrieving class information,
 * retrieving sections for a class, and adding new classes.
 */
public class ClassFirebaseController {

    private final FirebaseFirestore firebaseFirestore;
    /**
     * Constructor for the ClassFirebaseController.
     * @param firebaseFirestore The Firebase Firestore instance to use.
     */
    public ClassFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }

    /**
     * Retrieves a class from the Firebase Firstore database based on its ID.
     * @param _id The ID  of the class to retrieve.
     * @return A task representing  the asynchronous operation.
     */
    public Task<QuerySnapshot> getClassFromId(int _id){
        Query documentQuery = this.firebaseFirestore.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_ID, _id);
        return documentQuery.get();
    }
    /**
     * Retrieves all  classes associated with an institution based on the institution ID.
     * @param _id The ID of the institution.
     * @return A task representing the operation.
     */
    public Task<QuerySnapshot> getClassesFromInstitutionId(int _id){
        Query documentsQuery = this.firebaseFirestore.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_INSTITUTIONID, _id);
        return documentsQuery.get();
    }

    /**
     * Retrieves all sections associated with a class based on the class ID.
     * @param _id The ID of the class.
     * @return A task representing the operation.
     */
    public Task<QuerySnapshot> getSectionsFromClassId(int _id){
        return SectionFirebaseControllerSingleton.getInstance(firebaseFirestore).getSectionsFromClassId(_id);
    }
    /**
     * Adds a new class to the Firebase Firestore database.
     * @param class_ The class object to add.
     * @return A task representing the operation.
     */
    public Task<Void> addClass(Class class_){
        return this.firebaseFirestore.collection(Class.COLLECTION_CLASS).document(String.valueOf(class_.getId_())).set(class_);
    }

}

