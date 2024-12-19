package com.example.workdaybutbetter;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapter.RemoveFromSignUpQueueAdapter;
import com.example.application_data.UserDataSingleton;
import com.example.data_classes.Class;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity for removing classes from the sign-up queue.
 * This activity displays a list of classes that the user has signed up for
 * and allows them to remove themselves from the sign-up queue for those classes.
 * It also supports a counselor view for managing the sign-up queue of other users.

 */public class RemoveFromSignUpQueueActivity extends AppCompatActivity {

    public static final String EXTRA_COUNSELOR_VIEW = "EXTRA_COUNSELOR_VIEW";

    private AppCompatImageButton backButton;
    private ListView signUpQueueListView;

    private RemoveFromSignUpQueueAdapter removeFromSignUpQueueAdapter;

    private List<Class> signUpQueueData;

    private boolean counselorView = false;

    private FirebaseFirestore firebaseFirestoreInstance;

    private LoadingDialogFragment loadingDialogFragment;
    /**
     * Initializes the activity
     * for removing classes from the sign-up queue. It retrieves the sign-up queue
     * data, displays it in a list, and handles button clicks for removing classes.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_from_sign_up_queue);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_remove_from_sign_up_queue_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        loadingDialogFragment = new LoadingDialogFragment();

        counselorView = getIntent().getBooleanExtra(EXTRA_COUNSELOR_VIEW, false);
        signUpQueueData = new ArrayList<>();
        if(counselorView){
            signUpQueueData = new ArrayList<>();
        }else{
            loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

            if(UserDataSingleton.getInstance().getPendingClasses().isEmpty()){
                loadingDialogFragment.dismiss();
            }else{
                firebaseFirestoreInstance.collection(Class.COLLECTION_CLASS).whereIn(Class.FIELD_ID, UserDataSingleton.getInstance().getPendingClasses()).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                removeFromSignUpQueueAdapter.setSignUpQueueData(documentSnapshots.toObjects(Class.class));
                                loadingDialogFragment.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });
            }
        }

        removeFromSignUpQueueAdapter = new RemoveFromSignUpQueueAdapter(this, R.layout.activity_remove_from_sign_up_queue_list_item,  signUpQueueData);
        removeFromSignUpQueueAdapter.setOnButtonClickListener(new RemoveFromSignUpQueueAdapter.OnClickListener() {
            @Override
            public void onClick(View view, Class class_) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
                if(!counselorView){
                    UserDataSingleton.getInstance().removePendingClass(class_);
                    UserDataSingleton.getInstance().updateUserInDatabase(firebaseFirestoreInstance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    class_.removeMemberFromSignUpQueue(UserDataSingleton.getInstance());
                                    class_.updateClassInDatabase(firebaseFirestoreInstance)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    if(!UserDataSingleton.getInstance().getPendingClasses().isEmpty()){
                                                        firebaseFirestoreInstance.collection(Class.COLLECTION_CLASS).whereIn(Class.FIELD_ID, UserDataSingleton.getInstance().getPendingClasses()).get()
                                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                                                        removeFromSignUpQueueAdapter.setSignUpQueueData(documentSnapshots.toObjects(Class.class));
                                                                        loadingDialogFragment.dismiss();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        loadingDialogFragment.dismiss();
                                                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }
                                                                });
                                                    }else{
                                                        removeFromSignUpQueueAdapter.setSignUpQueueData(new ArrayList<>());
                                                        loadingDialogFragment.dismiss();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    loadingDialogFragment.dismiss();
                                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingDialogFragment.dismiss();
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                            });
                }else{

                }
            }
        });

        backButton = findViewById(R.id.activity_remove_from_sign_up_queue_navigation_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUpQueueListView = findViewById(R.id.activity_remove_from_sign_up_queue_listview);
        signUpQueueListView.setAdapter(removeFromSignUpQueueAdapter);


    }
}