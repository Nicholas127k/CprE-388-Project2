package com.example.firebase_controllers;

import androidx.annotation.NonNull;

import com.example.data_classes.Section;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SectionFirebaseController {

    private final FirebaseFirestore firebaseFirestoreInstance;

    public SectionFirebaseController(FirebaseFirestore firebaseFirestore){
        this.firebaseFirestoreInstance = firebaseFirestore;
    }

    public Task<QuerySnapshot> getSectionFromId(int _id){
        Query documentIdQuery = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo("id_", _id);
        return documentIdQuery.get();
    }

    public Task<QuerySnapshot> getSectionsFromClassId(int _id){
        Query documentsQueryId = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo("classId", _id);
        return documentsQueryId.get();
    }

    public Task<QuerySnapshot> getSectionsFromInstitutionId(int _id){
        Query documentsQueryId = this.firebaseFirestoreInstance.collection(Section.COLLECTION_SECTION).whereEqualTo("institutionId", _id);
        return documentsQueryId.get();
    }

}

class SectionFirebaseControllerSingleton{
    private static SectionFirebaseController sectionFirebaseController = null;

    private SectionFirebaseControllerSingleton(){}

    public static SectionFirebaseController getInstance(FirebaseFirestore firebaseFirestore){
        if(sectionFirebaseController == null){
            sectionFirebaseController = new SectionFirebaseController(firebaseFirestore);
        }

        return sectionFirebaseController;
    }
}
