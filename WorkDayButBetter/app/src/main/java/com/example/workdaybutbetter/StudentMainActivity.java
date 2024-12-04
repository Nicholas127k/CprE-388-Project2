package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentMainActivity extends AppCompatActivity {

    private AppCompatImageButton profileButton;
    private AppCompatImageButton addClassButton;
    private AppCompatImageButton scheduleButton;

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

        profileButton = findViewById(R.id.activity_student_main_navigation_profile_button);
        addClassButton = findViewById(R.id.activity_student_main_navigation_add_class_button);
        scheduleButton = findViewById(R.id.activity_student_main_navigation_schedule_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toUserProfileIntent = new Intent(StudentMainActivity.this, UserProfileActivity.class);
                startActivity(toUserProfileIntent);
            }
        });
    }
}