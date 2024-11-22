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
    private TextView textView2;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        textView2 = findViewById(R.id.textView2);
        textView2.setVisibility(View.INVISIBLE);
        usernmameEditText = findViewById(R.id.signup_username_edt);
        signUpButton = findViewById(R.id.signUpbutton);
        passwordEditText = findViewById(R.id.signup_password_edt);
        passwordConfirmEditText = findViewById(R.id.signup_confirm_edt);
        emailEditText = findViewById(R.id.emailthe1st);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = usernmameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = passwordConfirmEditText.getText().toString().trim();
                //test.setText("success");
                /* when login button is pressed, use intent to switch to Login Activity */
                if(password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpScreen.this, task -> {
                                if (task.isSuccessful()) {
                                    // User created successfully, save user details in Firestore
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Example: Store additional user data in Firestore (like username)
                                        String username = username1.split("@")[0];  // Simple username from email
                                        Random random = new Random();
                                        int randomNumber = random.nextInt();
                                        // Create a user object
                                        User newUser = new User(username, email, 0, randomNumber);

                                        // Save user data in Firestore
                                        db.collection("users")
                                                .document(user.getUid())
                                                .set(newUser)
                                                .addOnSuccessListener(aVoid -> {
                                                    // Navigate to the login screen after successful sign up
                                                    Intent intent = new Intent(SignUpScreen.this, LogInScreen.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Handle Firestore failure
                                                    Toast.makeText(SignUpScreen.this, "Error saving user data.", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            });


                }
                else{
                    textView2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}