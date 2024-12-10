package com.example.workdaybutbetter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.adapter.ClassSearchListAdapter;
import com.example.application_data.UserInstitutionSingleton;
import com.example.data_classes.Class;
import com.example.firebase_controllers.ClassFirebaseControllerSingleton;
import com.example.workdaybutbetter.views.LoadingDialogFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class ClassSearchAcitvity extends AppCompatActivity {

    public static final String EXTRA_SIGNUP = "EXTRA_SIGNUP";

    private AppCompatImageButton backButton;

    private SearchView classSearchView;

    private ListView classSearchListView;
    private ClassSearchListAdapter classSearchListAdapter;

    private FirebaseFirestore firebaseFirestoreInstance;

    private LoadingDialogFragment loadingDialogFragment;

    private List<Class> classQueryResults;

    private boolean signUpSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_class_search_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpSearch = getIntent().getBooleanExtra(EXTRA_SIGNUP, false);

        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        classQueryResults = new ArrayList<>();

        loadingDialogFragment = new LoadingDialogFragment();

        classSearchListView = findViewById(R.id.activity_class_search_class_listview);
        classSearchListAdapter = new ClassSearchListAdapter(this, R.layout.activity_class_search_list_item, classQueryResults);
        classSearchListAdapter.setClassSearchListAdapterListener(new ClassSearchListAdapter.ClassSearchListAdapterListener() {
            @Override
            public void onClassClickListener(int position, Class class_) {
                if(signUpSearch){
                    Intent toClassSignUpIntent = new Intent(ClassSearchAcitvity.this, ClassSignUpActivity.class);
                    toClassSignUpIntent.putExtra(ClassSignUpActivity.EXTRA_CLASSDATA, class_);
                    startActivity(toClassSignUpIntent);
                }else{
                    Intent toClassViewIntent = new Intent(ClassSearchAcitvity.this, ClassViewActivity.class);
                    toClassViewIntent.putExtra(ClassViewActivity.EXTRA_CLASSDATA, class_);
                    startActivity(toClassViewIntent);
                }
            }
        });
        classSearchListView.setAdapter(classSearchListAdapter);

        firebaseFirestoreInstance.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_INSTITUTIONID, UserInstitutionSingleton.getInstance().getId_()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        List<Class> classesList = documentSnapshots.toObjects(Class.class);

                        classQueryResults.clear();
                        classQueryResults.addAll(classesList);

                        classSearchListAdapter.setQueryClassList(classQueryResults);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        backButton = findViewById(R.id.activity_class_search_navigation_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        classSearchView = findViewById(R.id.activity_class_search_searchview);
        classSearchView.requestFocus();
        classSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        classSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                firebaseFirestoreInstance.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_INSTITUTIONID, UserInstitutionSingleton.getInstance().getId_()).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                List<Class> classesList = documentSnapshots.toObjects(Class.class);

                                classQueryResults.clear();
                                classQueryResults.addAll(classesList);

                                classSearchListAdapter.setQueryClassList(classQueryResults);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                return false;
            }
        });

        classSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(s.isEmpty()){
                    classSearchView.clearFocus();
                    return false;
                }

                loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);

                List<Filter> classQueryFilters = new ArrayList<>();
                classQueryFilters.add(Filter.equalTo(Class.FIELD_DEPARTMENT, s));
                classQueryFilters.add(Filter.equalTo(Class.FIELD_NAME, s));

                try {
                    classQueryFilters.add(Filter.equalTo(Class.FIELD_CODE, Integer.valueOf(s)));
                } catch (NumberFormatException ignored) {}

                Filter[] classFilters = new Filter[classQueryFilters.size()];
                Filter[] classFiltersResult = classQueryFilters.toArray(classFilters);

                firebaseFirestoreInstance.collection(Class.COLLECTION_CLASS).whereEqualTo(Class.FIELD_INSTITUTIONID, UserInstitutionSingleton.getInstance().getId_()).where(Filter.or(
                                classFiltersResult
                )).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        List<Class> classesList = documentSnapshots.toObjects(Class.class);

                        classQueryResults.clear();
                        classQueryResults.addAll(classesList);

                        classSearchListAdapter.setQueryClassList(classQueryResults);
                        loadingDialogFragment.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        loadingDialogFragment.dismiss();
                    }
                });

                classSearchView.clearFocus();
                loadingDialogFragment.dismiss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}