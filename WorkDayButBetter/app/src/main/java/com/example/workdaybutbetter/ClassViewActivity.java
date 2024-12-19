package com.example.workdaybutbetter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapter.ClassViewMembersListAdapter;
import com.example.adapter.ClassViewSectionListAdapter;
import com.example.data_classes.Class;
import com.example.data_classes.Section;
import com.example.data_classes.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity for viewing class details.
 *
 * This activity displays the users class and allows you to view them, including its
 * title, description, members, and sections. It also provides navigation
 * to view the sign-up queue and administer credit for the class.
 *
 */
public class ClassViewActivity extends AppCompatActivity {

    public static final String EXTRA_CLASSDATA = "EXTRA_CLASSDATA";

    private AppCompatImageButton backButton;
    private AppCompatImageButton viewSignUpQueueButton;
    private AppCompatImageButton administerCreditButton;

    private TextView classTitleTextView;
    private TextView classDescriptionTextView;

    private ListView membersListView;
    private ClassViewMembersListAdapter classViewMembersListAdapter;
    private List<User> classMembersList;

    List<Section> classSectionsList;
    private ListView sectionsListView;
    private ClassViewSectionListAdapter classViewSectionListAdapter;

    private FirebaseFirestore firebaseFirestoreInstance;

    Class classData = null;
    /**
     * Initializes the activity, sets up UI elements, and displays class information.
     * It also handles navigation to other activities related to the class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_class_view_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        classData = getIntent().getSerializableExtra(EXTRA_CLASSDATA, Class.class);

        administerCreditButton = findViewById(R.id.activity_class_view_navigation_bar_administer_credit_button);
        viewSignUpQueueButton = findViewById(R.id.activity_class_view_navigation_bar_view_signupqueue_button);
        backButton = findViewById(R.id.activity_class_view_navigation_bar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        classTitleTextView = findViewById(R.id.activity_class_view_class_name_textview);
        classDescriptionTextView = findViewById(R.id.activity_class_view_class_description_textview);

        classTitleTextView.setText(classData.getDepartment() + " " + classData.getCode() + " : " + classData.getName());
        classDescriptionTextView.setText(classData.getDescription());

        classMembersList = new ArrayList<>();

        classMembersList.addAll(classData.getClassMembers());

        membersListView = findViewById(R.id.activity_class_view_members_list_listview);
        classViewMembersListAdapter = new ClassViewMembersListAdapter(this, R.layout.activity_class_view_member_list_item, classMembersList);
        membersListView.setAdapter(classViewMembersListAdapter);

        classSectionsList = new ArrayList<>(classData.getSectionBuckets().values());

        sectionsListView = findViewById(R.id.activity_class_view_sections_list_listview);
        classViewSectionListAdapter = new ClassViewSectionListAdapter(this, R.layout.activity_class_view_section_list_item, classSectionsList);
        sectionsListView.setAdapter(classViewSectionListAdapter);

        viewSignUpQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}