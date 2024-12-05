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

public class JoinInstitutionDialogFragment extends DialogFragment {

    private EditText joinCodeEditText;

    private FirebaseFirestore firebaseFirestoreInstance;

    private JoinInstitutionDialogFragmentRefreshListener joinInstitutionDialogFragmentRefreshListener;

    public interface JoinInstitutionDialogFragmentRefreshListener{
        public void onInstitutionRefresh(Institution institution);
    }

    public void setInstitutionRefreshListener(JoinInstitutionDialogFragmentRefreshListener joinInstitutionDialogFragmentRefreshListener){
        this.joinInstitutionDialogFragmentRefreshListener = joinInstitutionDialogFragmentRefreshListener;
    }

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
                                    Toast.makeText(getContext(), "Invalid Join Code", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                DocumentSnapshot institutionDocument = institutionDocuments.get(0);
                                Institution userInstitution = institutionDocument.toObject(Institution.class);

                                if(userInstitution == null){
                                    Toast.makeText(getContext(), "Error converting institution", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                UserFirebaseControllerSingleton.getInstance(firebaseFirestoreInstance).joinInstitution(UserDataSingleton.getInstance(), userInstitution)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                UserDataSingleton.getInstance().setInstitutionId(userInstitution.getId_());
                                                UserInstitutionSingleton.setInstance(userInstitution);
                                                joinInstitutionDialogFragmentRefreshListener.onInstitutionRefresh(UserInstitutionSingleton.getInstance());
                                                dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
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
