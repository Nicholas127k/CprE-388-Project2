package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.workdaybutbetter.R;

public class EditUserProfileInformationDialogFragment extends DialogFragment {

    public static final String TAG = "EditUserProfileInformationDialogFragment";

    private EditText editUserProfileEditText;

    private String userProfileEditTextHint = null;

    private EditUserProfileInformationListener editUserProfileInformationListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        View dialogView = layoutInflater.inflate(R.layout.fragment_edit_user_profile_information_dialog, null);

        editUserProfileEditText = dialogView.findViewById(R.id.fragment_edit_user_profile_information_dialog_edittext);
        if(userProfileEditTextHint != null){
            editUserProfileEditText.setHint(userProfileEditTextHint);
            alertDialogBuilder.setTitle(userProfileEditTextHint);
        }

        alertDialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(editUserProfileInformationListener != null){
                    editUserProfileInformationListener.onConfirmChange(editUserProfileEditText.getText().toString());
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        alertDialogBuilder.setView(dialogView);
        return alertDialogBuilder.create();
    }

    public void setUserProfileEditTextHint(String userProfileEditTextHint){
        this.userProfileEditTextHint = userProfileEditTextHint;
    }

    public interface EditUserProfileInformationListener{
        public void onConfirmChange(String editTextData);
    }

    public void setEditUserProfileInformationListener(EditUserProfileInformationListener editUserProfileInformationListener){
        this.editUserProfileInformationListener = editUserProfileInformationListener;
    }

}
