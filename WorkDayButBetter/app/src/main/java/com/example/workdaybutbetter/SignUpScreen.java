package com.example.workdaybutbetter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data_classes.User;
import com.example.data_classes.UserType;
import com.example.firebase_controllers.UserFirebaseControllerSingleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;
/**
 * Activity for user sign-up.
 *
 * This activity provides a sign-up screen where users can create a new account
 * by entering their details such as username, email, password, and user type.
 * It uses Firebase Authentication to create the user account and stores user
 * data in Firestore.
 */
public class SignUpScreen extends AppCompatActivity {
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText passwordConfirmEditText;
    private EditText usernmameEditText;

    private Button signUpButton;
    private AppCompatImageButton backButton;

    private Spinner userTypeSpinner;
    private ArrayAdapter<CharSequence> userTypeSpinnerAdapter;
    private String userTypeSelected;

    private FirebaseAuth firebaseAuthenticationInstance;
    private FirebaseFirestore firebaseFirestoreInstance;
    /**
     * Initializes the activity, sets up UI elements, and handles user interactions
     * for sign-up. It validates user input, creates a user account using Firebase,
     * stores user data, and handles navigation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_signup_screen_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        passwordEditText = findViewById(R.id.activity_signup_screen_password_edittext);
        passwordConfirmEditText = findViewById(R.id.activity_signup_screen_confirm_password_edittext);
        usernmameEditText = findViewById(R.id.activity_signup_screen_username_edittext);
        emailEditText = findViewById(R.id.activity_signup_screen_email_edittext);

        signUpButton = findViewById(R.id.activity_signup_screen_signup_button);
        backButton = findViewById(R.id.activity_signup_screen_navigation_back_button);

        userTypeSpinner = findViewById(R.id.activity_signup_screen_usertype_spinner);
        userTypeSpinnerAdapter = ArrayAdapter.createFromResource(
                this,
                        R.array.usertype,
                        R.layout.activity_sign_up_usertype_spinner_item
                );
        userTypeSpinnerAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeSpinnerAdapter);
        userTypeSelected = userTypeSpinnerAdapter.getItem(0).toString();

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userTypeSelected = userTypeSpinnerAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

                if(!password.equals(confirmPassword)){
                    passwordConfirmEditText.setError("Passwords do not match idiot");
                }
                firebaseAuthenticationInstance.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser userData = authResult.getUser();

                                if(userData == null){
                                    Toast.makeText(getApplicationContext(), "User Data is null", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                UserType userType = UserType.NONE;
                                if(userTypeSelected.equals("Student")){
                                    userType = UserType.STUDENT;
                                } else if(userTypeSelected.equals("Counselor")) {
                                    userType = UserType.COUNSELOR;
                                } else if(userTypeSelected.equals("Professor")){
                                    userType = UserType.PROFESSOR;
                                }

                                User newUserData = new User(
                                        userData.getUid(),
                                        username,
                                        userData.getEmail(),
                                        "",
                                        "",
                                        new ArrayList<>(),
                                        userType,
                                        -1,
                                        new ArrayList<>()
                                );
                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).addUser(newUserData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                passwordConfirmEditText.setError(e.toString());
                                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }
}