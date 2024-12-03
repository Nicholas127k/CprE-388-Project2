package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.data_classes.Class;
import com.example.workdaybutbetter.R;

import java.util.ArrayList;

public class ClassLIstAdapter extends ArrayAdapter<Class> {
    private final int layoutResource;
    private Context context1;
    public ClassLIstAdapter(Context context, int formatListview, ArrayList<Class> list) {
        super(context, formatListview, list);
        context1 = context;
        this.layoutResource = formatListview;
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
