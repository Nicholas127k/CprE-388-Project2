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

import com.example.data_classes.Section;
import com.example.workdaybutbetter.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
            this.sectionsPriorityList.add(i + 1);
        }

        sectionsListItemSpinnerAdapter = new ArrayAdapter<>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, this.spinnerData);
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

            sectionPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("ADAPTER", sectionsPriorityList.toString());
                    Log.d("ADAPTER", sectionPrioritySpinner.getSelectedItem().toString() + " " + position + " " + sectionsPriorityList.get(position));
                    if(!Integer.valueOf(sectionPrioritySpinner.getSelectedItem().toString()).equals(sectionsPriorityList.get(position))){
                        sectionsPriorityList.set(position, i + 1);
                        sectionPrioritySpinner.setSelection(sectionsPriorityList.get(position) - 1);
                        Log.d("ADAPTER", sectionsPriorityList.get(position).toString());
                        Log.d("ADAPTER", "\n\n");
                    }else{
                        sectionPrioritySpinner.setSelection(sectionsPriorityList.get(position) - 1);
                    }
                    Log.d("ADAPTER", String.valueOf(i) + String.valueOf(l));
//                    swapSections(position, i);notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });

            sectionPrioritySpinner.setAdapter(sectionsListItemSpinnerAdapter);

        }

        return resultView;
    }

    public void swapSections(int index1, int index2){
        Section sectionData1 = this.sectionsList.get(index1);
        Section sectionData2 = this.sectionsList.get(index2);

        this.sectionsList.set(index1, sectionData2);
        this.sectionsList.set(index2, sectionData1);
    }
}
