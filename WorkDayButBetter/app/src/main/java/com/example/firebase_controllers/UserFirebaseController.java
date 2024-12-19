package com.example.firebase_controllers;

import com.example.data_classes.Institution;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserFirebaseController {
    /**
     * A controller class for managing user-related operations in Firebase.
     * This class provides methods for interacting with the Firebase Firestore
     * database to perform operations such as adding users, retrieving user
     * information, joining institutions, and getting all professors from an
     * institution.
     */
    private final FirebaseFirestore firebaseFirestore;
    /**
     * Constructor for the UserFirebaseController.
     * @param firebaseFirestore The FirebaseFirestore instance to use.
     */
    public UserFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }
    /**
     * Adds a new user to the Firebase Firestore database.
     * @param user The user object to add.
     * @return A task representing the asynchronous operation.
     */
    public Task<Void> addUser(User user){
        return firebaseFirestore.collection(User.COLLECTION_USER).document(String.valueOf(user.getId_())).set(user);
    }
    /**
     * Retrieves a user from the Firebase Firestore database based on their ID.
     * @param _id The ID of the user to retrieve.
     * @return A task representing the asynchronous operation.
     */
    public Task<QuerySnapshot> getUserFromId(String _id){
        return firebaseFirestore.collection(User.COLLECTION_USER).whereEqualTo(User.FIELD_ID, _id).get();
    }
    /**
     * Updates a user's school ID in the Firebase Firestore database.
     * This method is used when a user joins an institution.
     * @param user       The user object to update.
     * @param institution The institution the user is joining.
     * @return A task representing the asynchronous operation.
     */
    public Task<Void> joinInstitution(User user, Institution institution){
        User newInstitutionUser = user.duplicate();
        newInstitutionUser.setInstitutionId(institution.getId_());
        return this.firebaseFirestore.collection(User.COLLECTION_USER).document(String.valueOf(newInstitutionUser.getId_())).set(newInstitutionUser);
    }
    /**
     * Retrieves all professors from an institution based on the institution ID.
     * @param institutionId The ID of the institution.
     * @return A task representing the asynchronous operation.
     */
    public Task<QuerySnapshot> getAllProfessorsFromInstitutionId(int institutionId){
        return firebaseFirestore.collection(User.COLLECTION_USER).whereEqualTo(User.FIELD_USERTYPE, UserType.PROFESSOR).whereEqualTo(User.FIELD_INSTITUTIONID, institutionId).get();
    }

}

