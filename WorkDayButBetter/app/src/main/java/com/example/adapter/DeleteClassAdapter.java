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
/**
 * An adapter for displaying a list of classes for deletion.
 *
 * This adapter extends the ArrayAdapter class and is responsible for
 * creating and managing the views that display class information in a list.
 * It is specifically designed for displaying classes that can be deleted.

 */
public class DeleteClassAdapter extends ArrayAdapter<Class> {
    private final Context context1;
    private final int layoutResource;
    /**
     * Constructor for the DeleteClassAdapter.
     *
     * @param context      The context in which the adapter is used.
     * @param layoutResource The resource ID of the layout to use for each list item.
     * @param list         The list of class objects to display.
     */
    public DeleteClassAdapter(Context context, int layoutResource, List<Class> list) {
        super(context, layoutResource, list);
        this.context1 = context;
        this.layoutResource = layoutResource;
    }
    /**
     * Gets the view for a specific position in the list.
     * This method inflates the layout for the list item, sets up the views
     * with the class information, and disables button interactions.
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view for the specified position.
     */
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

