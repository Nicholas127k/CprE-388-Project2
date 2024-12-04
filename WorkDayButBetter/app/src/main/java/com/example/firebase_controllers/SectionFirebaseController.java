package com.example.firebase_controllers;

import androidx.annotation.NonNull;

import com.example.data_classes.Class;
import com.example.data_classes.Section;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SectionFirebaseController {

    private final FirebaseFirestore firebaseFirestoreInstance;

    public SectionFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestoreInstance = firebaseFirestore;
    }

    public Task<QuerySnapshot> getSectionFromId(int _id){
        Query documentIdQuery = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo(Section.FIELD_ID, _id);
        return documentIdQuery.get();
    }

    public Task<QuerySnapshot> getSectionsFromClassId(int _id){
        Query documentsQueryId = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo(Section.FIELD_CLASSID, _id);
        return documentsQueryId.get();
    }

    public Task<QuerySnapshot> getSectionsFromInstitutionId(int _id){
        Query documentsQueryId = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo(Section.FIELD_INSTITUTIONID, _id);
        return documentsQueryId.get();
    }

    public Task<Void> addSection(Section section){
        return this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).document(String.valueOf(section.getId_())).set(section);
    }

}

