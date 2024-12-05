package com.example.workdaybutbetter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateClassFragment extends Fragment {
    private EditText createName;
    private EditText createNumber;
    private EditText createDescription;
    private EditText createTime;
    private Button create;
    private Spinner departmentSpinner;
    private ArrayAdapter<CharSequence> departmentSpinnerAdapter;
    private String selectedDepartment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_class, container, false);

        // Initialize the EditTexts and other views
        createName = view.findViewById(R.id.createName);
        createNumber = view.findViewById(R.id.createNumber);
        createDescription = view.findViewById(R.id.createDescription);
        createTime  = view.findViewById(R.id.createTime);
        create = view.findViewById(R.id.createClass);
        departmentSpinner = view.findViewById(R.id.createDepartment);

        // Set up the adapter for the department spinner
        departmentSpinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
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

        return view;
    }
}

