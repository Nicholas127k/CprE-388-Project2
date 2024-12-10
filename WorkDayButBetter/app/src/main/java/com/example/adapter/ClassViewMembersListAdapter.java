package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.data_classes.User;
import com.example.workdaybutbetter.R;

import java.util.List;

public class ClassViewMembersListAdapter extends ArrayAdapter<User> {

    private Context context;
    private int layoutId;
    private List<User> usersList;

    public ClassViewMembersListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.usersList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View resultView = convertView;
        User currentUser = getItem(position);

        if(resultView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(layoutId, null);
        }

        if(currentUser != null){
            TextView memberInformationTextView = resultView.findViewById(R.id.activity_class_view_member_list_item_member_information_textview);
            memberInformationTextView.setText(currentUser.getUsername());

            ConstraintLayout memberItemLayout = resultView.findViewById(R.id.activity_class_view_member_list_item_layout);
            memberItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        return resultView;
    }

    public void setUsersList(List<User> usersList){
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    public void addUser(User user){
        this.usersList.add(user);
    }

    public void clear(){
        this.usersList.clear();
    }

}
