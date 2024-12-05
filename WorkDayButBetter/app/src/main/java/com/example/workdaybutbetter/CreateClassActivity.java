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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data_classes.Section;
import com.example.workdaybutbetter.views.AddClassSectionDialogFragment;

public class CreateClassActivity extends AppCompatActivity {

    private EditText createName;
    private EditText createNumber;
    private EditText createDescription;
    private EditText createTime;
    private Button create;
    private Spinner departmentSpinner;
    private ArrayAdapter<CharSequence> departmentSpinnerAdapter;
    private String selectedDepartment;
    private AddClassSectionDialogFragment addClassSectionDialogFragment;

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

        // Initialize the EditTexts and other views
        createName = findViewById(R.id.createName);
        createNumber = findViewById(R.id.createNumber);
        createDescription = findViewById(R.id.createDescription);
        createTime  = findViewById(R.id.createTime);
        create = findViewById(R.id.createClass);
        departmentSpinner = findViewById(R.id.createDepartment);

        addClassSectionDialogFragment = new AddClassSectionDialogFragment();

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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassSectionDialogFragment.show(getSupportFragmentManager(), AddClassSectionDialogFragment.TAG);
            }
        });

        addClassSectionDialogFragment.setAddClassSectionDialogListener(new AddClassSectionDialogFragment.AddClassSectionDialogListener() {
            @Override
            public void onCompleteBuildingSection(Section section) {
                Toast.makeText(getApplicationContext(), section.getLabel(), Toast.LENGTH_LONG).show();
            }
        });
    }
}