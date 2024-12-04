package com.example.firebase_controllers;

import com.example.data_classes.Class;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ClassFirebaseController {

    private final FirebaseFirestore firebaseFirestore;

    public ClassFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestore = firebaseFirestore;
    }

    public Task<QuerySnapshot> getClassFromId(int _id){
        Query documentQuery = this.firebaseFirestore.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_ID, _id);
        return documentQuery.get();
    }

    public Task<QuerySnapshot> getClassesFromInstitutionId(int _id){
        Query documentsQuery = this.firebaseFirestore.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_INSTITUTIONID, _id);
        return documentsQuery.get();
    }

    public Task<QuerySnapshot> getSectionsFromClassId(int _id){
        return SectionFirebaseControllerSingleton.getInstance(firebaseFirestore).getSectionsFromClassId(_id);
    }

    public Task<Void> addClass(Class class_){
        return this.firebaseFirestore.collection(Class.COLLECTION_CLASS).document(String.valueOf(class_.getId_())).set(class_);
    }

}

class ClassFirebaseControllerSingleton{
    private static ClassFirebaseController classFirebaseController = null;

    private ClassFirebaseControllerSingleton(){}

    public ClassFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(classFirebaseController == null){
            classFirebaseController = new ClassFirebaseController(firebaseFirestore);
        }

        return classFirebaseController;
    }
}
