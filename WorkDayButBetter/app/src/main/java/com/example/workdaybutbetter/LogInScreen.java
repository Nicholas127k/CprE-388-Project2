package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


public class LogInScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button loginButton;
    private Button signUpButton;

    private FirebaseAuth firebaseAuthenticationInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // mQuery = query;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_screen);

        usernameEditText = findViewById(R.id.activity_login_screen_textedit_username);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.activity_login_screen_textedit_password);

        loginButton = findViewById(R.id.activity_login_screen_button_login);
        signUpButton = findViewById(R.id.activity_login_screen_button_signup);

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();

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
                        .addOnCompleteListener(LogInScreen.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuthenticationInstance.getCurrentUser();  // Get the current user
                            } else {
                                passwordEditText.setError("Authentication Failed: Incorrect Password");
                            }
                        });
            }
        });

    }
}