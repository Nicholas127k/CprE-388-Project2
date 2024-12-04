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

import com.example.data_classes.User;
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

                                                Toast.makeText(getApplicationContext(), userData.getUsername(), Toast.LENGTH_LONG).show();
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