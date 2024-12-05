package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.data_classes.Section;
import com.example.utilities.ClassSectionDayEnum;
import com.example.utilities.ClassSectionTimeRange;
import com.example.workdaybutbetter.R;

import java.util.ArrayList;
import java.util.List;

public class AddClassSectionDialogFragment extends DialogFragment {

    public static final String TAG = "ADD_CLASS_SECTION_DIALOG_FRAGMENT";

    public interface AddClassSectionDialogListener{
        public void onCompleteBuildingSection(Section section);
    }

    private AddClassSectionDialogListener addClassSectionDialogListener;

    public void setAddClassSectionDialogListener(AddClassSectionDialogListener listener){
        this.addClassSectionDialogListener = listener;
    }

    private EditText sectionLabelEditText;
    private EditText toTimeEditText;
    private EditText fromTimeEditText;

    private CheckBox mondayCheckbox;
    private CheckBox tuesdayCheckbox;
    private CheckBox wednesdayCheckbox;
    private CheckBox thursdayCheckbox;
    private CheckBox fridayCheckbox;

    private AppCompatButton addSectionButton;
    private AppCompatButton cancelButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.fragment_add_class_section_dialog, null);
        alertDialogBuilder.setView(dialogView);

        sectionLabelEditText = dialogView.findViewById(R.id.fragment_add_class_section_dialog_section_label_edittext);
        toTimeEditText = dialogView.findViewById(R.id.fragment_add_class_section_dialog_to_time_edittext);
        fromTimeEditText = dialogView.findViewById(R.id.fragment_add_class_section_dialog_from_time_edittext);

        mondayCheckbox = dialogView.findViewById(R.id.fragment_add_class_section_dialog_monday_checkbox);
        tuesdayCheckbox = dialogView.findViewById(R.id.fragment_add_class_section_dialog_tuesday_checkbox);
        wednesdayCheckbox = dialogView.findViewById(R.id.fragment_add_class_section_dialog_wednesday_checkbox);
        thursdayCheckbox = dialogView.findViewById(R.id.fragment_add_class_section_dialog_thursday_checkbox);
        fridayCheckbox = dialogView.findViewById(R.id.fragment_add_class_section_dialog_friday_checkbox);

        addSectionButton = dialogView.findViewById(R.id.fragment_add_class_section_dialog_create_section_button);
        cancelButton = dialogView.findViewById(R.id.fragment_add_class_section_dialog_cancel_section_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        addSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassSectionTimeRange classSectionTimeRange = new ClassSectionTimeRange();
                List<ClassSectionDayEnum> sectionDays = new ArrayList<>();

                Section addedSection = new Section();
                addedSection.setId_(1000);

                addedSection.setLabel(sectionLabelEditText.getText().toString());

                if(mondayCheckbox.isChecked()){
                    sectionDays.add(ClassSectionDayEnum.MONDAY);
                }

                if(tuesdayCheckbox.isChecked()){
                    sectionDays.add(ClassSectionDayEnum.TUESDAY);
                }

                if(wednesdayCheckbox.isChecked()){
                    sectionDays.add(ClassSectionDayEnum.WEDNESDAY);
                }

                if(thursdayCheckbox.isChecked()){
                    sectionDays.add(ClassSectionDayEnum.THURSDAY);
                }

                if(fridayCheckbox.isChecked()){
                    sectionDays.add(ClassSectionDayEnum.FRIDAY);
                }

                classSectionTimeRange.setDays(sectionDays);
                classSectionTimeRange.setStartTime(toTimeEditText.getText().toString());
                classSectionTimeRange.setEndTime(fromTimeEditText.getText().toString());

                addedSection.setTime(classSectionTimeRange);

                addClassSectionDialogListener.onCompleteBuildingSection(addedSection);

                dismiss();

            }
        });


        return alertDialogBuilder.create();
    }

}
