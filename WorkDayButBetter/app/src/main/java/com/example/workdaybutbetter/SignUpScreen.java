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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

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
    private FirebaseFirestore db;

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

                if(!username.equals(confirmPassword)){
                    passwordConfirmEditText.setError("Passwords do not match idiot");
                }

                firebaseAuthenticationInstance.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

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