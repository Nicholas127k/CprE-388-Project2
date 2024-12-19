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
import com.example.application_data.UserInstitutionSingleton;
import com.example.data_classes.Institution;
import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.example.workdaybutbetter.views.JoinInstitutionDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * Activity for displaying and managing user profile information.
 *
 * This activity allows users to view their profile details, such as username,
 * email, first name, last name, user type, and institution. It also provides
 * options for logging out and joining an institution.
 */
public class UserProfileActivity extends AppCompatActivity {

    private AppCompatImageButton backButton;
    private AppCompatButton logoutButton;
    private AppCompatButton joinInstitutionButton;

    private TextView usernameText;
    private TextView emailText;
    private TextView firstnameText;
    private TextView lastnameText;
    private TextView userTypeText;
    private TextView institutionText;

    private JoinInstitutionDialogFragment joinInstitutionDialogFragment;

    private FirebaseAuth firebaseAuthenticationInstance;
    /**
     * for the user profile screen. It displays user profile information, handles
     * logout functionality, and allows users to join an institution.
     */
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

        joinInstitutionDialogFragment = new JoinInstitutionDialogFragment();
        joinInstitutionDialogFragment.setInstitutionRefreshListener(new JoinInstitutionDialogFragment.JoinInstitutionDialogFragmentRefreshListener() {
            @Override
            public void onInstitutionRefresh(Institution institution, int status, String errorMessage) {

                if(status == JoinInstitutionDialogFragment.FAILURE){
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    return;
                }

                if(institution.getId_() == -1){
                    institutionText.setText("Institution:    None");
                }else{
                    institutionText.setText("Institution:    "+institution.getInstitutionName());
                }
            }
        });

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
                UserInstitutionSingleton.getInstance().clear();

                Intent toApplicationLoginScreen = new Intent(UserProfileActivity.this, LogInScreen.class);
                startActivity(toApplicationLoginScreen);
            }
        });

        joinInstitutionButton = findViewById(R.id.activity_user_profile_join_institution_button);
        joinInstitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinInstitutionDialogFragment.show(getSupportFragmentManager(), "JOIN_INSTITUTION_DIALOG");
            }
        });

        usernameText = findViewById(R.id.activity_user_profile_username_text);
        emailText = findViewById(R.id.activity_user_profile_email_text);
        firstnameText = findViewById(R.id.activity_user_profile_firstname_text);
        lastnameText = findViewById(R.id.activity_user_profile_lastname_text);
        userTypeText = findViewById(R.id.activity_user_profile_usertype_text);
        institutionText = findViewById(R.id.activity_user_profile_institution_text);

        User userData = UserDataSingleton.getInstance();
        Institution institution = UserInstitutionSingleton.getInstance();

        usernameText.setText("Username:  " + userData.getUsername());
        emailText.setText("Email:   " + userData.getEmail());
        firstnameText.setText("Firstname:   " + userData.getFirstname());
        lastnameText.setText("Lastname:   " + userData.getLastname());
        userTypeText.setText("User Type:   " + userData.getUserType().toString());

        if(institution.getId_() == -1){
            institutionText.setText("Institution:    None");
        }else{
            institutionText.setText("Institution:    "+institution.getInstitutionName());
        }

    }
}