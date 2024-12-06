package com.example.workdaybutbetter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data_classes.Section;
import com.example.workdaybutbetter.views.AddClassSectionDialogFragment;
import com.example.workdaybutbetter.views.ViewSectionsDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CreateClassActivity extends AppCompatActivity {

    private EditText classNameEditText;
    private EditText classNumberEditText;
    private EditText classDescriptionEditText;

    private Button createClassButton;
    private AppCompatImageButton backButton;
    private AppCompatButton addSectionButton;
    private AppCompatButton viewSectionsButton;

    private Spinner departmentSpinner;
    private ArrayAdapter<CharSequence> departmentSpinnerAdapter;
    private String selectedDepartment;

    private AddClassSectionDialogFragment addClassSectionDialogFragment;
    private ViewSectionsDialogFragment viewSectionsDialogFragment;

    private List<Section> sectionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_create_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_create_class_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sectionsList = new ArrayList<>();

        // Initialize the EditTexts and other views
        classNameEditText = findViewById(R.id.fragment_create_class_classname_edittext);
        classNumberEditText = findViewById(R.id.fragment_create_class_classnumber_edittext);
        classDescriptionEditText = findViewById(R.id.fragment_create_class_description_edittext);

        viewSectionsButton = findViewById(R.id.fragment_create_class_view_sections_button);
        addSectionButton = findViewById(R.id.fragment_create_class_add_section_button);
        createClassButton = findViewById(R.id.fragment_create_class_create_class_button);
        backButton = findViewById(R.id.fragment_create_class_navigation_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        departmentSpinner = findViewById(R.id.fragment_create_class_department_spinner);

        addClassSectionDialogFragment = new AddClassSectionDialogFragment();
        viewSectionsDialogFragment = new ViewSectionsDialogFragment();

        // Set up the adapter for the department spinner
        departmentSpinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.departments,  // The array of department names defined in strings.xml
                android.R.layout.simple_spinner_item // Layout for each item
        );
        departmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentSpinnerAdapter);

        // Set default selection (optional)
        selectedDepartment = departmentSpinnerAdapter.getItem(0).toString();

        // Handle item selection from the spinner
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedDepartment = departmentSpinnerAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case where nothing is selected (optional)
            }
        });

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        addClassSectionDialogFragment.setAddClassSectionDialogListener(new AddClassSectionDialogFragment.AddClassSectionDialogListener() {
            @Override
            public void onCompleteBuildingSection(Section section) {
                sectionsList.add(section);
            }
        });

        addSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassSectionDialogFragment.show(getSupportFragmentManager(), AddClassSectionDialogFragment.TAG);
            }
        });

        viewSectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSectionsDialogFragment.setDialogSections(sectionsList);
                viewSectionsDialogFragment.show(getSupportFragmentManager(), ViewSectionsDialogFragment.TAG);
            }
        });
    }
}