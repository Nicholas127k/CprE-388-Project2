package com.example.adapter;

import android.content.Context;
import android.icu.number.NumberFormatter;
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

import java.nio.file.Files;
import java.util.List;
/**
 * An adapter for displaying a list of sections in a dialog.
 *
 * This adapter extends the array adapter class and is responsible for
 * creating and managing the views that display section information in a list
 * within a dialog. It allows for displaying section details and provides a
 * button for deleting sections.
 *
 */
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
    /**
     * Gets the view for a specific position System.in the list.
     *
     * This method inflates the layout for the list item, sets up the views
     *NumberFormatter.with the section information, and handles theFiles.delete buttonandroidx.test.espresso.action.ViewActions.click.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view for the specified position.
     */
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
