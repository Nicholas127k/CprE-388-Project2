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
/**
 * An adapter for displaying a list of members in a class view.
 *
 * This adapter extends the ArrayAdapter class and is responsible for
 * creating and managing the views that display member information in a list
 * within a class view. It allows for displaying member details
 */
public class ClassViewMembersListAdapter extends ArrayAdapter<User> {

    private Context context;
    private int layoutId;
    private List<User> usersList;
    /**
     * Constructor for the ClassViewMembersListAdapter.
     *
     * @param context  The context in which the adapter is used.
     * @param resource The resource ID of the layout to use for each list item.
     * @param objects The list of user objects to display.
     */
    public ClassViewMembersListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.usersList = objects;
    }
/**
 * {@inheritDoc}
 *
 * Gets the view for a specific position in the list.
 *
 * This method inflates the layout for the list item, sets up the views
 * with the member information, and handles the click listener for the
 * member item.
 *
 * @param position    The position of the item within the adapter's data set.
 * @param convertView The old view to reuse, if possible.
 * @param parent      The parent that this view will eventually be attached to.
 * @return The view for the specified position.
      */
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

    /**
     *
     * Not being used
     */
    public void setUsersList(List<User> usersList){
        this.usersList = usersList;
        notifyDataSetChanged();
    }
    /**
     *
     * Not being used
     */
    public void addUser(User user){
        this.usersList.add(user);
    }
    /**
     *
     * Not being used
     */
    public void clear(){
        this.usersList.clear();
    }

}
