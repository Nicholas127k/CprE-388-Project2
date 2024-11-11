package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // mQuery = query;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_screen);
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);
        loginButton = findViewById(R.id.LogInbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test.setText("success");
                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

    }
}