package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.data_classes.Class;
import com.example.workdaybutbetter.R;

import java.util.ArrayList;
import java.util.List;
/**
 * An adapter for displaying a list of classes in a remove-from-sign-up-queue list.
 *
 * This adapter provides a view for each class in the list, allowing users to remove
 * themselves from the sign-up queue for that class.
 */

public class RemoveFromSignUpQueueAdapter extends ArrayAdapter<Class> {

    private Context context;
    private int layoutId;
    private List<Class> classData;
    /**
     * Constructor for the RemoveFromSignUpQueueAdapter.
     *
     * @param context  The context of the activity.
     * @param resource The layout resource ID for the list item view.
     * @param objects  The list of Class objects to display.
     */
    public RemoveFromSignUpQueueAdapter(@NonNull Context context, int resource, @NonNull List<Class> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.classData = objects;
    }
    /**
     * Interface for handling click events on the remove button.
     */
    public interface OnClickListener{
        public void onClick(View view, Class class_);
    }

    private OnClickListener onClickListener;
    /**
     * Sets the click listener for the remove button.
     *
     * @param onClickListener The click listener to set.
     */
    public void setOnButtonClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    /**
     * Sets up each individual list item view.
     *
     * @param position
     *  @param convertView
     *  @param parent
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View resultView = convertView;

        if(resultView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(R.layout.activity_remove_from_sign_up_queue_list_item, null);
        }

        Class currentClass = getItem(position);

        if(currentClass != null){
            TextView classDataTextView = resultView.findViewById(R.id.activity_remove_from_sign_up_queue_class_data);
            AppCompatImageButton removeFromQueueButton = resultView.findViewById(R.id.activity_remove_from_sign_up_queue_remove_button);

            classDataTextView.setText(currentClass.getDepartment() + " " + currentClass.getCode() + " : " + currentClass.getName());
            removeFromQueueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view, currentClass);
                }
            });
        }

        return resultView;
    }
    /**
     * sets the adapter with new sign-up queue data.
     *
     * @param classes The new list of Class objects to display.
     */
    public void setSignUpQueueData(List<Class> classes){
        List<Class> dataCopy = new ArrayList<>(classes);
        this.clear();
        this.classData = dataCopy;
        this.addAll(this.classData);
        notifyDataSetChanged();
    }
}
