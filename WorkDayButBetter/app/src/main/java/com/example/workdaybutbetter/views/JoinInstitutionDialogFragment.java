package com.example.workdaybutbetter.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.application_data.UserDataSingleton;
import com.example.application_data.UserInstitutionSingleton;
import com.example.data_classes.Institution;
import com.example.firebase_controllers.InstitutionFirebaseControllerSingleton;
import com.example.firebase_controllers.UserFirebaseControllerSingleton;
import com.example.workdaybutbetter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
/**
 * A dialog fragment for joining an institution.
 *
 * This fragment displays a dialog where the user can enter a join code to
 * join an institution. If the join code is valid, the user's institution
 * ID is updated in the database and the fragment is dismissed.
 */
public class JoinInstitutionDialogFragment extends DialogFragment {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    private EditText joinCodeEditText;

    private FirebaseFirestore firebaseFirestoreInstance;
    /**
     * A listener interface for handling institution refresh events.
     */
    private JoinInstitutionDialogFragmentRefreshListener joinInstitutionDialogFragmentRefreshListener;
    /**
     * Called when the institution needs to be refreshed.
     */
    public interface JoinInstitutionDialogFragmentRefreshListener{
        public void onInstitutionRefresh(Institution institution, int status, String errorMessage);
    }
    /**
     * Sets the listener for handling institution refresh events.
     * @param joinInstitutionDialogFragmentRefreshListener The listener to set.
     */
    public void setInstitutionRefreshListener(JoinInstitutionDialogFragmentRefreshListener joinInstitutionDialogFragmentRefreshListener){
        this.joinInstitutionDialogFragmentRefreshListener = joinInstitutionDialogFragmentRefreshListener;
    }
    /**

     * Creates the dialog for joining an institution.
     *
     * This method inflates the dialog layout, sets up the views, and handles
     * the button click events.
     *
     * @param savedInstanceState The saved instance state.
     * @return The created dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.fragment_join_institution_dialog, null);

        joinCodeEditText = dialogView.findViewById(R.id.fragment_join_institution_join_code_edittext);
        firebaseFirestoreInstance = FirebaseFirestore.getInstance();

        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setMessage("Enter Institution Join Code");

        alertDialogBuilder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                InstitutionFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance)
                        .checkJoinCode(joinCodeEditText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                List<DocumentSnapshot> institutionDocuments = documentSnapshots.getDocuments();
                                if(institutionDocuments.isEmpty()){
                                    joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance(), FAILURE, "Invalid Join Code");
                                    return;
                                }

                                DocumentSnapshot institutionDocument = institutionDocuments.get(0);
                                Institution userInstitution = institutionDocument.toObject(Institution.class);

                                if(userInstitution == null){
                                    joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance(), FAILURE, "Error Converting Institution");
                                    return;
                                }

                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).joinInstitution(UserDataSingleton.getInstance(), userInstitution)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                UserDataSingleton.getInstance().setInstitutionId(userInstitution.getId_());
                                                UserInstitutionSingleton.setInstance(userInstitution);
                                                joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance(), SUCCESS, "");
                                                dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance(), FAILURE, e.toString());
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance(), FAILURE, e.toString());
                            }
                        });
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

}
