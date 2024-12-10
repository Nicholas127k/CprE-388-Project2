package com.example.workdaybutbetter;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapter.ClassSignUpSectionListAdapter;
import com.example.data_classes.Class;
import com.example.data_classes.Section;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ClassSignUpActivity extends AppCompatActivity {

    public static final String EXTRA_CLASSDATA = "EXTRA_CLASSDATA";

    private AppCompatImageButton backButton;
    private AppCompatButton signUpButton;

    private ListView sectionsListView;
    private ClassSignUpSectionListAdapter classSignUpSectionListAdapter;
    private List<Section> sectionsList;

    private TextView classTitleTextView;
    private TextView classDescriptionTextView;

    private LoadingDialogFragment loadingDialogFragment;

    private FirebaseFirestore firebaseFirestoreInstance;

    private Class classData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_class_sign_up_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        loadingDialogFragment = new LoadingDialogFragment();

        classData = getIntent().getSerializableExtra(EXTRA_CLASSDATA, Class.class);

        backButton = findViewById(R.id.activity_class_sign_up_navigation_bar_back_button);
        signUpButton = findViewById(R.id.activity_class_sign_up_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sectionsListView = findViewById(R.id.activity_class_sign_up_section_selection_listview);
        sectionsList = new ArrayList<>(classData.getSectionBuckets().values());
        classSignUpSectionListAdapter = new ClassSignUpSectionListAdapter(this, R.layout.activity_class_sign_up_section_priority_select_list_item, sectionsList);
        sectionsListView.setAdapter(classSignUpSectionListAdapter);

        classTitleTextView = findViewById(R.id.activity_class_sign_up_class_information_textview);
        classDescriptionTextView = findViewById(R.id.activity_class_sign_up_class_description_textview);

        if(classData != null){
            classTitleTextView.setText(classData.getDepartment() + " " + classData.getCode() + " : " + classData.getName());
            classDescriptionTextView.setText(classData.getDescription());
        }else{
            classTitleTextView.setText("Class == null");
            classDescriptionTextView.setText("Class == null");
        }

    }
}