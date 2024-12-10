package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.data_classes.Section;
import com.example.workdaybutbetter.R;

import org.w3c.dom.Text;

import java.util.List;

public class ViewSectionsDialogSectionsListAdapter extends ArrayAdapter<Section> {

    private int listLayout;
    private Context context;
    private List<Section> sectionsData;

    public ViewSectionsDialogSectionsListAdapter(@NonNull Context context, int resource, @NonNull List<Section> objects) {
        super(context, resource, objects);

        this.context = context;
        this.listLayout = resource;
        this.sectionsData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View resultView = convertView;
        Section currentSection = getItem(position);

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(listLayout, null);
        }

        if(currentSection != null){
            resultView.setTag(currentSection.getId_());
            TextView currentSectionInformationTextView = resultView.findViewById(R.id.fragment_view_sections_dialog_section_information_textview);
            AppCompatImageButton deleteSectionButton = resultView.findViewById(R.id.fragment_view_sections_dialog_delete_section_button);

            if(currentSectionInformationTextView != null){
                currentSectionInformationTextView.setText(currentSection.getLabel() + " : " + currentSection.getSectionSize() + "\n" + currentSection.getTime().getStartTime() + " - " + currentSection.getTime().getEndTime());
            }

            if(deleteSectionButton != null){
                deleteSectionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sectionsData.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        return resultView;
    }
}
