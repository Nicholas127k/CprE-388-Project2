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
import com.google.firebase.database.DatabaseReference;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_available_classes_screen);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        mViewList = (ListView) findViewById(R.id.classSearch);

    }
    private void fetchClassesFromFirebase() {
        // Loop through the response (assuming 'response' is a JSONArray)
        for (int i = 0; i < response.length(); i++) {
            try {
                // Retrieve the JSON object representing each class
                JSONObject friendObj = response.getJSONObject(i);
                JSONObject friendUserObj = friendObj.getJSONObject("classes");

                // Extract the class details from the JSON object
                int userId = friendUserObj.getInt("uId");
                String department = friendUserObj.getString("department");
                long code = friendUserObj.getLong("code");

                // Assuming 'name' and 'description' are also in the same "classes" object
                String name = friendUserObj.getString("name"); // Assuming 'name' exists in the data
                String description = friendUserObj.getString("description"); // Assuming 'description' exists

                // Dont need it so leave it blank
                List<User> signUpQueue = new ArrayList<>();
                Class clas = new Class(userId, department, code, name, description, signUpQueue);

                // Now create the Class object and add it to the classList

                classList.add(clas);

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }




    }

