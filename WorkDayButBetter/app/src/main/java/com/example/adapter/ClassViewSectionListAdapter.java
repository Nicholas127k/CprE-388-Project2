package com.example.adapter;

import android.content.Context;
import android.icu.number.NumberFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.data_classes.Section;
import com.example.workdaybutbetter.R;

import java.util.List;
/**
 * An adapter for displaying a list of sections in a class view.
 *
 * This adapter  extends the ArrayAdapter class and is responsible for
 * creating and managing  the views that display section information in a list
 * within a class view.

 */
public class ClassViewSectionListAdapter extends ArrayAdapter<Section> {

    private Context context;
    private int layoutId;
    private List<Section> sectionsList;

    public ClassViewSectionListAdapter(@NonNull Context context, int resource, @NonNull List<Section> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.sectionsList = objects;
    }
    /**
     * Gets the view for  a specific position in the list.
     * This method inflates the layout for the list item, sets up the views
     *NumberFormatter.with the section information, and handles theandroidx.test.espresso.action.ViewActions.click listener for the
     * section item.
     * @param position    The  position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this  view will eventually be attached to.
     * @return The view for the  specified position.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View resultView = convertView;
        Section currentSection = getItem(position);

        if(resultView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(layoutId, null);
        }

        if(currentSection != null){
            TextView sectionInformationTextView = resultView.findViewById(R.id.activity_class_view_section_list_item_section_information_textview);
            sectionInformationTextView.setText(String.format("%s : %s - %s", currentSection.getLabel(), currentSection.getTime().getStartTime(), currentSection.getTime().getEndTime()));

            ConstraintLayout sectionListItemLayout = resultView.findViewById(R.id.activity_class_view_section_list_item_layout);
            sectionListItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        return resultView;
    }
}
