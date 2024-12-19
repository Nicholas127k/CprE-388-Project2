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
import com.example.firebase_controllers.UserFirebaseController;
import com.example.firebase_controllers.UserFirebaseControllerSingleton;
import com.example.workdaybutbetter.views.EditUserProfileInformationDialogFragment;
import com.example.workdaybutbetter.views.JoinInstitutionDialogFragment;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

    private AppCompatImageButton editUsernameButton;
    private EditUserProfileInformationDialogFragment editUserNameDialogFragment;

    private AppCompatImageButton editFirstNameButton;
    private EditUserProfileInformationDialogFragment editFirstNameDialogFragment;

    private AppCompatImageButton editLastNameButton;
    private EditUserProfileInformationDialogFragment editLastNameDialogFragment;

    private JoinInstitutionDialogFragment joinInstitutionDialogFragment;

    private LoadingDialogFragment loadingDialogFragment;

    private FirebaseAuth firebaseAuthenticationInstance;
    private FirebaseFirestore firebaseFirestoreInstance;
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
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        loadingDialogFragment = new LoadingDialogFragment();

        editUserNameDialogFragment = new EditUserProfileInformationDialogFragment();
        editUserNameDialogFragment.setUserProfileEditTextHint("Username");

        editFirstNameDialogFragment = new EditUserProfileInformationDialogFragment();
        editFirstNameDialogFragment.setUserProfileEditTextHint("First Name");

        editLastNameDialogFragment = new EditUserProfileInformationDialogFragment();
        editLastNameDialogFragment.setUserProfileEditTextHint("Last Name");

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

        editUsernameButton = findViewById(R.id.activity_user_profile_username_edit_button);
        editUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserNameDialogFragment.show(getSupportFragmentManager(), EditUserProfileInformationDialogFragment.TAG);
            }
        });

        editUserNameDialogFragment.setEditUserProfileInformationListener(new EditUserProfileInformationDialogFragment.EditUserProfileInformationListener() {
            @Override
            public void onConfirmChange(String editTextData) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                if(editTextData.equals(UserDataSingleton.getInstance().getUsername())){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Username is the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(editTextData.isEmpty()){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Username field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDataSingleton.getInstance().setUsername(editTextData);

                UserDataSingleton.getInstance().updateUserInDatabase(firebaseFirestoreInstance).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getUserFromId(UserDataSingleton.getInstance().getId_())
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        UserDataSingleton.setInstance(documentSnapshots.getDocuments().get(0).toObject(User.class));
                                        setTextViews(UserDataSingleton.getInstance(), UserInstitutionSingleton.getInstance());
                                        loadingDialogFragment.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDialogFragment.dismiss();
                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialogFragment.dismiss();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                });

            }
        });

        editFirstNameButton = findViewById(R.id.activity_user_profile_firstname_edit_button);
        editFirstNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFirstNameDialogFragment.show(getSupportFragmentManager(), EditUserProfileInformationDialogFragment.TAG);
            }
        });

        editFirstNameDialogFragment.setEditUserProfileInformationListener(new EditUserProfileInformationDialogFragment.EditUserProfileInformationListener() {
            @Override
            public void onConfirmChange(String editTextData) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                if(editTextData.equals(UserDataSingleton.getInstance().getFirstname())){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Firstname is the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(editTextData.isEmpty()){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Firstname field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDataSingleton.getInstance().setFirstname(editTextData);

                UserDataSingleton.getInstance().updateUserInDatabase(firebaseFirestoreInstance).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getUserFromId(UserDataSingleton.getInstance().getId_())
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                                UserDataSingleton.setInstance(documentSnapshots.getDocuments().get(0).toObject(User.class));
                                                setTextViews(UserDataSingleton.getInstance(), UserInstitutionSingleton.getInstance());
                                                loadingDialogFragment.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingDialogFragment.dismiss();
                                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialogFragment.dismiss();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });
            }
        });

        editLastNameButton = findViewById(R.id.activity_user_profile_lastname_edit_button);
        editLastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLastNameDialogFragment.show(getSupportFragmentManager(), EditUserProfileInformationDialogFragment.TAG);
            }
        });

        editLastNameDialogFragment.setEditUserProfileInformationListener(new EditUserProfileInformationDialogFragment.EditUserProfileInformationListener() {
            @Override
            public void onConfirmChange(String editTextData) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                if(editTextData.equals(UserDataSingleton.getInstance().getLastname())){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Lastname is the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(editTextData.isEmpty()){
                    loadingDialogFragment.dismiss();
                    Toast.makeText(getApplicationContext(), "Lastname field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDataSingleton.getInstance().setLastname(editTextData);

                UserDataSingleton.getInstance().updateUserInDatabase(firebaseFirestoreInstance).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).getUserFromId(UserDataSingleton.getInstance().getId_())
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                                UserDataSingleton.setInstance(documentSnapshots.getDocuments().get(0).toObject(User.class));
                                                setTextViews(UserDataSingleton.getInstance(), UserInstitutionSingleton.getInstance());
                                                loadingDialogFragment.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingDialogFragment.dismiss();
                                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialogFragment.dismiss();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });
            }
        });

        User userData = UserDataSingleton.getInstance();
        Institution institution = UserInstitutionSingleton.getInstance();

        setTextViews(userData, institution);

    }

    private void setTextViews(User user, Institution institution){
        usernameText.setText("Username:  " + user.getUsername());
        emailText.setText("Email:   " + user.getEmail());
        firstnameText.setText("Firstname:   " + user.getFirstname());
        lastnameText.setText("Lastname:   " + user.getLastname());
        userTypeText.setText("User Type:   " + user.getUserType().toString());

        if(institution.getId_() == -1){
            institutionText.setText("Institution:    None");
        }else{
            institutionText.setText("Institution:    "+institution.getInstitutionName());
        }
    }
}