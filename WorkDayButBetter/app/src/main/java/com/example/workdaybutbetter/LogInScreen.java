package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application_data.UserDataSingleton;
import com.example.application_data.UserInstitutionSingleton;
import com.example.data_classes.Institution;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.example.firebase_controllers.InstitutionFirebaseController;
import com.example.firebase_controllers.InstitutionFirebaseControllerSingleton;
import com.example.firebase_controllers.UserFirebaseControllerSingleton;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class LogInScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button loginButton;
    private Button signUpButton;
    private AppCompatButton createInstitutionButton;

    private FirebaseAuth firebaseAuthenticationInstance;
    private FirebaseFirestore firebaseFirestoreInstance;

    private LoadingDialogFragment loadingDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_login_screen_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadingDialogFragment = new LoadingDialogFragment();

        usernameEditText = findViewById(R.id.activity_login_screen_textedit_username);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.activity_login_screen_textedit_password);

        loginButton = findViewById(R.id.activity_login_screen_button_login);
        signUpButton = findViewById(R.id.activity_login_screen_button_signup);
        createInstitutionButton = findViewById(R.id.activity_login_screen_button_create_institution);

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });

        createInstitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreateInstitutionIntent = new Intent(LogInScreen.this, CreateInstitutionActivity.class);
                startActivity(toCreateInstitutionIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                String email = usernameEditText.getText().toString().trim();  // Get username (email)
                String password = passwordEditText.getText().toString().trim();  // Get password

                // Check if username or password is empty
                if (email.isEmpty() || password.isEmpty()) {
                    loadingDialogFragment.dismiss();
                    Toast.makeText(LogInScreen.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use Firebase Authentication to sign in
                firebaseAuthenticationInstance.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser firebaseUser = authResult.getUser();

                                if(firebaseUser == null){
                                    loadingDialogFragment.dismiss();
                                    Toast.makeText(getApplicationContext(), "User information is null", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getUserFromId(firebaseUser.getUid())
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                                List<DocumentSnapshot> userDataDocuments = documentSnapshots.getDocuments();
                                                if(userDataDocuments.isEmpty()){
                                                    loadingDialogFragment.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Unable to find user information", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                DocumentSnapshot userDataDocument = userDataDocuments.get(0);
                                                User userData = userDataDocument.toObject(User.class);

                                                if(userData == null){
                                                    loadingDialogFragment.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Error while converting user data", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                UserDataSingleton.setInstance(userData);

                                                if(userData.getUserType() == UserType.NONE){
                                                    loadingDialogFragment.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_LONG).show();
                                                }else if(userData.getUserType() == UserType.STUDENT){
                                                    loadingDialogFragment.dismiss();
                                                    Intent userLoginSuccessIntent = new Intent(LogInScreen.this, StudentMainActivity.class);
                                                    startActivity(userLoginSuccessIntent);

                                                }else if(userData.getUserType() == UserType.COUNSELOR){
                                                    loadingDialogFragment.dismiss();
                                                    Intent userLoginSuccessIntent = new Intent(LogInScreen.this, AdvisorMainScreenActivity.class);
                                                    startActivity(userLoginSuccessIntent);

                                                }

                                                int userInstitutionId = UserDataSingleton.getInstance().getInstitutionId();
                                                if(userInstitutionId != Institution.DEFAULT_ID){
                                                    InstitutionFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getInstitutionFromId(userInstitutionId)
                                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onSuccess(QuerySnapshot documentSnapshots) {

                                                                    List<DocumentSnapshot> institutionDocuments = documentSnapshots.getDocuments();
                                                                    if(institutionDocuments.isEmpty()){
                                                                        loadingDialogFragment.dismiss();
                                                                        Toast.makeText(getApplicationContext(), "No Institution Found", Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }

                                                                    DocumentSnapshot userInstitutionDocument = institutionDocuments.get(0);
                                                                    Institution userInstitution = userInstitutionDocument.toObject(Institution.class);

                                                                    if(userInstitution == null){
                                                                        loadingDialogFragment.dismiss();
                                                                        Toast.makeText(getApplicationContext(), "Error while converting institution", Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }

                                                                    UserInstitutionSingleton.setInstance(userInstitution);
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    loadingDialogFragment.dismiss();
                                                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                    loadingDialogFragment.dismiss();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingDialogFragment.dismiss();
                                                passwordEditText.setError(e.toString());
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialogFragment.dismiss();
                                passwordEditText.setError(e.toString());
                            }
                        });

            }
        });

    }
}