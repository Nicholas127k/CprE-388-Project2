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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application_data.UserDataSingleton;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.example.firebase_controllers.UserFirebaseControllerSingleton;
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

    private FirebaseAuth firebaseAuthenticationInstance;
    private FirebaseFirestore firebaseFirestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_screen);

        usernameEditText = findViewById(R.id.activity_login_screen_textedit_username);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.activity_login_screen_textedit_password);

        loginButton = findViewById(R.id.activity_login_screen_button_login);
        signUpButton = findViewById(R.id.activity_login_screen_button_signup);

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString().trim();  // Get username (email)
                String password = passwordEditText.getText().toString().trim();  // Get password

                // Check if username or password is empty
                if (email.isEmpty() || password.isEmpty()) {
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
                                    Toast.makeText(getApplicationContext(), "User information is null", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getUserFromId(firebaseUser.getUid())
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                                List<DocumentSnapshot> userDataDocuments = documentSnapshots.getDocuments();
                                                if(userDataDocuments.isEmpty()){
                                                    Toast.makeText(getApplicationContext(), "Unable to find user information", Toast.LENGTH_LONG).show();
                                                    return;
                                                }

                                                DocumentSnapshot userDataDocument = userDataDocuments.get(0);
                                                User userData = userDataDocument.toObject(User.class);

                                                if(userData == null){
                                                    Toast.makeText(getApplicationContext(), "Error while converting user data", Toast.LENGTH_LONG).show();
                                                    return;
                                                }

                                                UserDataSingleton.setInstance(userData);

                                                if(userData.getUserType() == UserType.NONE){
                                                    Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_LONG).show();
                                                }else if(userData.getUserType() == UserType.STUDENT){

                                                    Intent userLoginSuccessIntent = new Intent(LogInScreen.this, StudentMainActivity.class);
                                                    startActivity(userLoginSuccessIntent);

                                                }else if(userData.getUserType() == UserType.COUNSELOR){

                                                    Intent userLoginSuccessIntent = new Intent(LogInScreen.this, AdvisorMainScreen.class);
                                                    startActivity(userLoginSuccessIntent);

                                                }

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                passwordEditText.setError(e.toString());
                            }
                        });
            }
        });

    }
}