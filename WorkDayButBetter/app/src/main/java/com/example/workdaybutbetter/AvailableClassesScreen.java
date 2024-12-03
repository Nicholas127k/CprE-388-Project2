package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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
        fetchClassesFromFirebase();
        // Set up the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        mViewList.setAdapter(adapter);

         // Fetch the classes when the activity is created
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minipulateClassList();
            }
        });
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
    private void minipulateClassList() {
        String query = searchEditText.getText().toString().toLowerCase();  // Get the search input and convert to lowercase

        List<Class> filteredList = new ArrayList<>();

        // Loop through each class and check if it matches the search query
        for (Class clas : classList) {
            // Check if any class field matches the search query (name, department, or description)
            if (clas.getName().toLowerCase().contains(query) ||
                    clas.getDepartment().toLowerCase().contains(query) ||
                    clas.getDescription().toLowerCase().contains(query)) {
                filteredList.add(clas); // Add matching classes to the filtered list
            }
        }

        // Update the class list with the filtered list
        classList.clear();
        classList.addAll(filteredList);

        // Notify the adapter that the data has changed, so the ListView is updated
        adapter.notifyDataSetChanged();
    }
    private void initializeListAddAdapter() {
        //FriendsAddListAdapter friendsListAdapter = new FriendsAddListAdapter(this, R.layout.listview_deleteclass, mViewList);
        //ListView mViewList = findViewById(R.id.listView7);
        //mViewList.setAdapter((ListAdapter) friendsListAdapter);
    }
}

