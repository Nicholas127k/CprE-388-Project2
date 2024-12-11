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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application_data.UserInstitutionSingleton;
import com.example.data_classes.Class;
import com.example.data_classes.Section;
import com.example.firebase_controllers.ClassFirebaseControllerSingleton;
import com.example.id_generator.IdGenerator;
import com.example.workdaybutbetter.views.AddClassSectionDialogFragment;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.example.workdaybutbetter.views.ViewSectionsDialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CreateClassActivity extends AppCompatActivity {

    private EditText classNameEditText;
    private EditText classNumberEditText;
    private EditText classDescriptionEditText;
    private EditText classDepartmentEditText;

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

    private FirebaseFirestore firebaseFirestoreInstance;

    private LoadingDialogFragment loadingDialogFragment;

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

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        sectionsList = new ArrayList<>();

        // Initialize the EditTexts and other views
        classNameEditText = findViewById(R.id.fragment_create_class_classname_edittext);
        classNumberEditText = findViewById(R.id.fragment_create_class_classnumber_edittext);
        classDescriptionEditText = findViewById(R.id.fragment_create_class_description_edittext);

        loadingDialogFragment = new LoadingDialogFragment();

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

        classDepartmentEditText = findViewById(R.id.fragment_create_class_department_edittext);

        addClassSectionDialogFragment = new AddClassSectionDialogFragment();
        viewSectionsDialogFragment = new ViewSectionsDialogFragment();

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                int classId = IdGenerator.generateId();

                Class newClass = new Class(
                        classId,
                        classDepartmentEditText.getText().toString(),
                        Long.decode(classNumberEditText.getText().toString()),
                        classNameEditText.getText().toString(),
                        classDescriptionEditText.getText().toString(),
                        new ArrayList<>(),
                        UserInstitutionSingleton.getInstance().getId_(),
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                for(int i = 0; i < sectionsList.size(); ++i){
                    sectionsList.get(i).setClassId(classId);
                    sectionsList.get(i).setInstitutionId(UserInstitutionSingleton.getInstance().getId_());
                    int status = newClass.addSection(sectionsList.get(i));

                    if(status == Class.SECTION_ALREADY_IN_CLASS){
                        loadingDialogFragment.dismiss();
                        Toast.makeText(getApplicationContext(), "Section Already Exists in Class", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                ClassFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).addClass(newClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                loadingDialogFragment.dismiss();
            }
        });

        addClassSectionDialogFragment.setAddClassSectionDialogListener(new AddClassSectionDialogFragment.AddClassSectionDialogListener() {
            @Override
            public void onCompleteBuildingSection(Section section) {
                int status = 0;

                for(int i = 0; i < sectionsList.size(); ++i){
                    if(section.getLabel().equals(sectionsList.get(i).getLabel())){
                        status = Class.SECTION_ALREADY_IN_CLASS;
                        break;
                    }
                }

                if(status == Class.SECTION_ALREADY_IN_CLASS){
                    Toast.makeText(getApplicationContext(), "Section Already Exists in Class", Toast.LENGTH_LONG).show();
                    return;
                }

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