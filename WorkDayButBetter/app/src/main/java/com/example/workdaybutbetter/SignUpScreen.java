package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class SignUpScreen extends AppCompatActivity {
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText passwordConfirmEditText;
    private EditText usernmameEditText;

    private Button signUpButton;
    private Button backButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);

        passwordEditText = findViewById(R.id.activity_signup_screen_password_edittext);
        passwordConfirmEditText = findViewById(R.id.activity_signup_screen_confirm_password_edittext);
        usernmameEditText = findViewById(R.id.activity_signup_screen_username_edittext);
        emailEditText = findViewById(R.id.activity_signup_screen_email_edittext);

        signUpButton = findViewById(R.id.activity_signup_screen_signup_button);
        backButton = findViewById(R.id.activity_signup_screen_navigation_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernmameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = passwordConfirmEditText.getText().toString().trim();

                if(!username.equals(confirmPassword)){
                    passwordConfirmEditText.setError("Passwords do not match idiot");
                }

                /* when login button is pressed, use intent to switch to Login Activity */
//                if(password.equals(confirmPassword)) {
//                    mAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(SignUpScreen.this, task -> {
//                                if (task.isSuccessful()) {
//                                    // User created successfully, save user details in Firestore
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if (user != null) {
//                                        // Example: Store additional user data in Firestore (like username)
//                                        String username = username1.split("@")[0];  // Simple username from email
//                                        Random random = new Random();
//                                        int randomNumber = random.nextInt();
//                                        // Create a user object
//                                        User newUser = new User(username, email, 0, randomNumber);
//
//                                        // Save user data in Firestore
//                                        db.collection("users")
//                                                .document(user.getUid())
//                                                .set(newUser)
//                                                .addOnSuccessListener(aVoid -> {
//                                                    // Navigate to the login screen after successful sign up
//                                                    Intent intent = new Intent(SignUpScreen.this, LogInScreen.class);
//                                                    startActivity(intent);
//                                                    finish();
//                                                })
//                                                .addOnFailureListener(e -> {
//                                                    // Handle Firestore failure
//                                                    Toast.makeText(SignUpScreen.this, "Error saving user data.", Toast.LENGTH_SHORT).show();
//                                                });
//                                    }
//                                }
//                            });
//
//
//                }
//                else{
//                    textView2.setVisibility(View.VISIBLE);
//                }
            }
        });
    }
}