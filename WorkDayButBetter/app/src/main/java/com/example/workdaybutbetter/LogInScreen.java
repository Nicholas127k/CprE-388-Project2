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
    /**
     * @author Nicholas Kirschbaum
     * Takes input frim edit text and uses it as Username
     */
    private EditText usernameEditText;
    /**
     * @author Nicholas Kirschbaum
     * Takes input frim edit text and user it as password
     */
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // mQuery = query;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_screen);
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);
        loginButton = findViewById(R.id.LogInbutton);

       // mAuth = FirebaseAuth.getInstance();  // Initialize FirebaseAuth

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
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogInScreen.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign-in successful, go to the main activity
                                FirebaseUser user = mAuth.getCurrentUser();  // Get the current user
                                Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                                startActivity(intent);  // Navigate to MainActivity
                                finish();  // Optional: Close the login activity
                            } else {
                                // If sign-in fails, display a message
                                Toast.makeText(LogInScreen.this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}