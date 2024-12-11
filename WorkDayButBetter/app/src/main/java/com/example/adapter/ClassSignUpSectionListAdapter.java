package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.data_classes.Class;
import com.example.data_classes.Section;
import com.example.workdaybutbetter.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSignUpSectionListAdapter extends ArrayAdapter<Section> {

    private Context context;
    private int layoutId;
    private List<Section> sectionsList;
    private List<Integer> sectionsPriorityList;

    private ArrayAdapter<String> sectionsListItemSpinnerAdapter;
    private List<String> spinnerData;

    public ClassSignUpSectionListAdapter(@NonNull Context context, int resource, @NonNull List<Section> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.sectionsList = objects;

        this.spinnerData = new ArrayList<>();
        for(int i = 0; i < this.sectionsList.size(); ++i){
            this.spinnerData.add(String.valueOf(i + 1));
        }

        this.sectionsPriorityList = new ArrayList<>();
        for(int i = 0; i < this.sectionsList.size(); ++i){
            this.sectionsPriorityList.add(1);
        }

        sectionsListItemSpinnerAdapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, this.spinnerData);

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

            Spinner sectionPrioritySpinner = resultView.findViewById(R.id.activity_class_sign_up_selection_priority_select_List_item_spinner);
            TextView sectionInformationTextView = resultView.findViewById(R.id.activity_class_sign_up_selection_priority_select_list_item_section_information_textview);
            sectionInformationTextView.setText(currentSection.getLabel() + " : " + currentSection.getTime().getStartTime() + " - " + currentSection.getTime().getEndTime());
            sectionPrioritySpinner.setAdapter(sectionsListItemSpinnerAdapter);

            sectionPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    sectionsPriorityList.set(position, Integer.valueOf(spinnerData.get((int)l)));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });


        }

        return resultView;
    }

    public List<Integer> getSectionPriorities(){
        return this.sectionsPriorityList;
    }
}
