package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.workdaybutbetter.R;
/**
 * A dialog fragment for displaying the sign-up queue for a class.
 * This fragment displays a dialog that shows the current queue of students
 * waiting to sign up for a class. The dialog can be used to view the queue
 * and potentially manage it.
 */
public class ClassViewSignUpQueueViewDialogFragment extends DialogFragment {
    /**
     * Creates the dialog for displaying the sign-up queue.
     * This method inflates the dialog layout and sets up the views.
     * @param savedInstanceState The saved instance state.
     * @return The created dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View alertDialogView = layoutInflater.inflate(R.layout.fragment_activity_class_view_sign_up_queue_dialog, null);

        return alertDialogBuilder.create();
    }

}
