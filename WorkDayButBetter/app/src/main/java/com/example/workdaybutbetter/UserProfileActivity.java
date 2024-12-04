package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application_data.UserDataSingleton;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private AppCompatImageButton backButton;
    private AppCompatButton logoutButton;

    private TextView usernameText;
    private TextView emailText;
    private TextView firstnameText;
    private TextView lastnameText;
    private TextView userTypeText;

    private FirebaseAuth firebaseAuthenticationInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_user_profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();

        backButton = findViewById(R.id.activity_user_profile_navigation_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logoutButton = findViewById(R.id.activity_user_profile_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentFirebaseUser = firebaseAuthenticationInstance.getCurrentUser();

                if(currentFirebaseUser == null){
                    Toast.makeText(getApplicationContext(), "Error while trying to logout", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                firebaseAuthenticationInstance.signOut();

                if(firebaseAuthenticationInstance.getCurrentUser() != null){
                    Toast.makeText(getApplicationContext(), "Unsuccessful SignOut", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                UserDataSingleton.getInstance().clear();

                Intent toApplicationLoginScreen = new Intent(UserProfileActivity.this, LogInScreen.class);
                startActivity(toApplicationLoginScreen);
            }
        });

        usernameText = findViewById(R.id.activity_user_profile_username_text);
        emailText = findViewById(R.id.activity_user_profile_email_text);
        firstnameText = findViewById(R.id.activity_user_profile_firstname_text);
        lastnameText = findViewById(R.id.activity_user_profile_lastname_text);
        userTypeText = findViewById(R.id.activity_user_profile_usertype_text);

        User userData = UserDataSingleton.getInstance();

        usernameText.setText("Username:  " + userData.getUsername());
        emailText.setText("Email:   " + userData.getEmail());
        firstnameText.setText("Firstname:   " + userData.getFirstname());
        lastnameText.setText("Lastname:   " + userData.getLastname());
        userTypeText.setText("User Type:   " + userData.getUserType().toString());

    }
}