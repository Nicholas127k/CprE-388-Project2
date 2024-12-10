package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdvisorMainScreenActivity extends AppCompatActivity {

    private AppCompatImageButton profileButton;
    private LinearLayout createAClassButton;
    private LinearLayout removeAClassButton;
    private LinearLayout searchAClassButton;

    private FirebaseAuth firebaseAuthenticationInstance;
    private FirebaseFirestore firebaseFirestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advisor_main_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_advisor_main_screen_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuthenticationInstance = FirebaseAuth.getInstance();
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        profileButton = findViewById(R.id.activity_advisor_main_screen_navigation_profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfileScreenIntent = new Intent(AdvisorMainScreenActivity.this, UserProfileActivity.class);
                startActivity(toProfileScreenIntent);
            }
        });

        searchAClassButton = findViewById(R.id.activity_advisor_main_screen_class_search_button_layout);
        createAClassButton = findViewById(R.id.activity_advisor_main_screen_add_class_button_layout);
        removeAClassButton = findViewById(R.id.activity_advisor_main_screen_remove_class_button_layout);

        createAClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreateAClassIntent = new Intent(AdvisorMainScreenActivity.this, CreateClassActivity.class);
                startActivity(toCreateAClassIntent);
            }
        });

        searchAClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSearchAClassIntent = new Intent(AdvisorMainScreenActivity.this, ClassSearchAcitvity.class);
                startActivity(toSearchAClassIntent);
            }
        });

    }
}