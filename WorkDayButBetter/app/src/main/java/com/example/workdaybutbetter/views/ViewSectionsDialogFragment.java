package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.adapter.ViewSectionsDialogSectionsListAdapter;
import com.example.data_classes.Section;
import com.example.workdaybutbetter.R;

import java.util.ArrayList;
import java.util.List;

public class ViewSectionsDialogFragment extends DialogFragment {

    public static final String TAG = "VIEW_SECTIONS_DIALOG_FRAGMENT";

    private List<Section> dialogSections = new ArrayList<>();

    public void setDialogSections(List<Section> dialogSections){
        this.dialogSections = dialogSections;
    }

    public interface ViewSectionsDialogFragmentListener{
        public void onSectionsRefresh(List<Section> newSections);
    }

    private ViewSectionsDialogFragmentListener viewSectionsDialogFragmentListener;

    public void setViewSectionsDialogFragmentListener(ViewSectionsDialogFragmentListener viewSectionsDialogFragmentListener){
        this.viewSectionsDialogFragmentListener = viewSectionsDialogFragmentListener;
    }

    private AppCompatButton exitButton;

    private ListView sectionsListView;
    private ViewSectionsDialogSectionsListAdapter viewSectionsDialogSectionsListAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View viewSectionsDialogView = layoutInflater.inflate(R.layout.fragment_view_sections_dialog, null);

        exitButton = viewSectionsDialogView.findViewById(R.id.fragment_view_sections_dialog_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        sectionsListView = viewSectionsDialogView.findViewById(R.id.fragment_view_sections_dialog_sections_list);
        viewSectionsDialogSectionsListAdapter = new ViewSectionsDialogSectionsListAdapter(requireActivity(), R.layout.fragment_view_sections_dialog_list_item, dialogSections);
        sectionsListView.setAdapter(viewSectionsDialogSectionsListAdapter);

        alertDialogBuilder.setView(viewSectionsDialogView);
        return alertDialogBuilder.create();
    }

}
