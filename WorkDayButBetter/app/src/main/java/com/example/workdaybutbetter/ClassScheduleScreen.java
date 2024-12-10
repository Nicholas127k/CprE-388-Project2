package com.example.workdaybutbetter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.example.data_classes.Class;
import com.example.data_classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassScheduleScreen extends AppCompatActivity {

    private Button exitButton;
    private DatabaseReference mDatabase;
    private List<Class> classList = new ArrayList<>();
    private ArrayAdapter<Class> adapter;
    private ListView mViewList;
    private String userId; // Assuming userId is available. You can get it from Firebase Authentication or pass it from another activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_schedule_screen);

        // Assume the user ID is passed from the previous activity
        userId = getIntent().getStringExtra("USER_ID");

        exitButton = findViewById(R.id.button);
        mViewList = findViewById(R.id.classTotal);

        // Setup button click
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ClassScheduleScreen.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        // Initialize the adapter with an empty list (this will be updated after fetching classes)
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        mViewList.setAdapter(adapter);

        // Fetch the user's classes from Firebase
        fetchUserClassesFirebase();

        // Set up ListView item click listener to show class details in a popup
        mViewList.setOnItemClickListener((parent, view, position, id) -> {
            Class selectedClass = classList.get(position);
            showClassDetailsPopup(selectedClass);
        });
    }

    // Fetch the user's classes from Firebase based on their userId
    private void fetchUserClassesFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("classes");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classList.clear(); // Clear the list before adding new data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String classId = snapshot.getKey(); // Assuming class ID is used as the key for each class
                    fetchClassDetails(classId);
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ClassScheduleScreen", "loadClasses:onCancelled", databaseError.toException());
            }
        });
    }

    // Fetch class details based on class ID
    private void fetchClassDetails(String classId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("classes").child(classId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Class clas = dataSnapshot.getValue(Class.class);
                if (clas != null) {
                    classList.add(clas); // Add the class to the list
                    adapter.notifyDataSetChanged(); // Notify adapter to refresh the list view
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ClassScheduleScreen", "loadClassDetails:onCancelled", databaseError.toException());
            }
        });
    }

    // Show class details in a popup dialog when an item is clicked
    private void showClassDetailsPopup(Class selectedClass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(selectedClass.getName());

        String classDetails = "Department: " + selectedClass.getDepartment() + "\n"
                + "Code: " + selectedClass.getCode() + "\n"
                + "Description: " + selectedClass.getDescription() + "\n"
                + "Number of Students Signed Up: " + selectedClass.getSignUpQueue().size();

        builder.setMessage(classDetails);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
