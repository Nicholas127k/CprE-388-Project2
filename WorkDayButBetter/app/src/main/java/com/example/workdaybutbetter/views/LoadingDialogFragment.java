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
/**
 * A dialog fragment for displaying a loading indicator.
 *
 * This fragment displays a dialog with a loading indicator to inform the
 * user that a background operation is in progress. The dialog is typically
 * displayed while waiting for data to load or for a task to complete.
 */
public class LoadingDialogFragment extends DialogFragment {

    public static final String TAG = "LOADING_DIALOG_FRAGMENT";
    /**
     * Creates the dialog for displaying the loading indicator.
     *
     * This method inflates the dialog layout, sets up the views, and makes
     * the dialog non-cancelable. It also sets the background of the dialog
     * to transparent.
     *
     * @param savedInstanceState The saved instance state.
     * @return The created dialog.
     */
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
