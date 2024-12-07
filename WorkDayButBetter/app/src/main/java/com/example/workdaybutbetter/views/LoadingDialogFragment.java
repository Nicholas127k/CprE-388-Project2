package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.workdaybutbetter.R;

public class LoadingDialogFragment extends DialogFragment {

    public static final String TAG = "LOADING_DIALOG_FRAGMENT";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View loadingDialogView = layoutInflater.inflate(R.layout.fragment_loading_dialog, null);

        alertDialogBuilder.setView(loadingDialogView);
        alertDialogBuilder.setCancelable(false);

        Dialog alertDialogView = alertDialogBuilder.create();
        alertDialogView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return alertDialogView;
    }

}
