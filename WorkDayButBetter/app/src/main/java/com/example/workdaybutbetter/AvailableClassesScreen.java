package com.example.workdaybutbetter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data_classes.Class;
import com.example.data_classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AvailableClassesScreen extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private DatabaseReference mDatabase;
    private List<Class> classList = new ArrayList<>();
    private ArrayAdapter<Class> adapter;
    private ListView mViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_classes_screen);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        mViewList = findViewById(R.id.classSearch);

        // Set up the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        mViewList.setAdapter(adapter);

        fetchClassesFromFirebase();  // Fetch the classes when the activity is created
    }

    private void fetchClassesFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("classes");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Class clas = snapshot.getValue(Class.class);
                    if (clas != null) {
                        classList.add(clas);
                    }
                }
                adapter.notifyDataSetChanged();  // Notify the adapter to refresh the ListView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("AvailableClassesScreen", "loadClasses:onCancelled", databaseError.toException());
            }
        });
    }
}

