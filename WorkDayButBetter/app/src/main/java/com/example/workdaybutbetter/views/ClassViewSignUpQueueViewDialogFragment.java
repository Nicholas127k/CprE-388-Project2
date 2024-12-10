package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.workdaybutbetter.R;

public class ClassViewSignUpQueueViewDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View alertDialogView = layoutInflater.inflate(R.layout.fragment_activity_class_view_sign_up_queue_dialog, null);

        return alertDialogBuilder.create();
    }

}
