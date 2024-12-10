package com.example.adapter;

import android.content.Context;
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
