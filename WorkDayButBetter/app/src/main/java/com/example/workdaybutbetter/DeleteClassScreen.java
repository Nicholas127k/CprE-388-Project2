package com.example.workdaybutbetter;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.adapter.DeleteClassAdapter;
import com.example.data_classes.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * Not being used lol
 */
public class DeleteClassScreen extends AppCompatActivity {

    private ListView mViewList;
    private final List<Class> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class_screen);

        mViewList = findViewById(R.id.listView7);

        // Add a sample class to the list
//        classList.add(new Class(0, "Sample Class", 0, "Sample Teacher", null, null));

        // Initialize the adapter and set it to the ListView
        initializeListAddAdapter();
    }

    private void initializeListAddAdapter() {
        DeleteClassAdapter adapter = new DeleteClassAdapter(this, R.layout.listview_deleteclass, classList);
        mViewList.setAdapter(adapter);
    }
}