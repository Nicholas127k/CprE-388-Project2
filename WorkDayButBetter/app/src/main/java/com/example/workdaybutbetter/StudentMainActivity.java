package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentMainActivity extends AppCompatActivity {

    private AppCompatImageButton profileButton;
    private LinearLayout addClassButton;
    private LinearLayout viewScheduleButton;

    private LoadingDialogFragment loadingDialogFragment;

    private FirebaseFirestore firebaseFirestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_student_main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        loadingDialogFragment = new LoadingDialogFragment();

        profileButton = findViewById(R.id.activity_student_main_navigation_profile_button);
        addClassButton = findViewById(R.id.activity_student_main_add_class_button_layout);
        viewScheduleButton = findViewById(R.id.activity_student_main_view_schedule_button_layout);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toUserProfileIntent = new Intent(StudentMainActivity.this, UserProfileActivity.class);
                startActivity(toUserProfileIntent);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toClassSignUpIntent = new Intent(StudentMainActivity.this, ClassSearchAcitvity.class);
                toClassSignUpIntent.putExtra(ClassSearchAcitvity.EXTRA_SIGNUP, true);
                startActivity(toClassSignUpIntent);
            }
        });
    }
}