package com.example.workdaybutbetter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data_classes.Institution;
import com.example.firebase_controllers.InstitutionFirebaseController;
import com.example.firebase_controllers.InstitutionFirebaseControllerSingleton;
import com.example.id_generator.IdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CreateInstitutionActivity extends AppCompatActivity {

    private AppCompatButton createInstitutionButton;
    private AppCompatImageButton createInstitutionBackButton;

    private EditText institutionNameEditText;
    private EditText institutionJoinCodeEditText;

    private FirebaseFirestore firebaseFirestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_institution);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_create_institution_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        createInstitutionButton = findViewById(R.id.activity_create_institution_create_institution_button);
        createInstitutionBackButton = findViewById(R.id.activity_create_institution_navigation_back_button);
        createInstitutionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        institutionNameEditText = findViewById(R.id.activity_create_institution_name_edittext);
        institutionJoinCodeEditText = findViewById(R.id.activity_create_institution_code_edittext);

        createInstitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstitutionFirebaseController institutionFirebaseController = InstitutionFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance);

                Institution newInstitution = new Institution(IdGenerator.generateId(), institutionNameEditText.getText().toString(), institutionJoinCodeEditText.getText().toString());

                institutionFirebaseController.verifyInstitutionExists(newInstitution)
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.getResult().isEmpty()){
                                            institutionFirebaseController.createInstitution(newInstitution)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Institution Parameters Exist", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

            }
        });
    }
}