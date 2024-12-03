package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.data_classes.Class;
import com.example.workdaybutbetter.R;

import java.util.List;

public class DeleteClassAdapter extends ArrayAdapter<Class> {
    private final Context context1;
    private final int layoutResource;

    public DeleteClassAdapter(Context context, int layoutResource, List<Class> list) {
        super(context, layoutResource, list);
        this.context1 = context;
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the class object for this position

        Class classObj = getItem(position);

        // Inflate the layout
        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        convertView = layoutInflater.inflate(layoutResource, parent, false);

        // Set up the views
        TextView user = convertView.findViewById(R.id.delete1Title);
        Button btn = convertView.findViewById(R.id.bigBoyButton);

        // Disable button interactions
        btn.setFocusable(false);
        btn.setClickable(false);

        // Set the text for the class name
        if (classObj != null) {
            user.setText("Class: " + classObj.getName());
        }

        // You can add any other logic for the button click here if needed
        btn.setOnClickListener(view -> {
            // Logic for button click (if needed)
        });

        return convertView;
    }
}

