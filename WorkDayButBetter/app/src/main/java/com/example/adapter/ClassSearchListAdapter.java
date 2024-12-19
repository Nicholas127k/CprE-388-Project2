package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.data_classes.Class;
import com.example.workdaybutbetter.ClassViewActivity;
import com.example.workdaybutbetter.R;

import java.util.List;
/**
 * An adapter for displaying a list of classes in a class search.
 *
 * This adapter extends the ArrayAdapter} class and is responsible for
 * creating  and managing the views that display class information in a list
 * within a class search.
 */
public class ClassSearchListAdapter extends ArrayAdapter<Class> {
    /**
     * Called when a class item is clicked.
     *
     */
    public interface ClassSearchListAdapterListener{
        public void onClassClickListener(int position, Class class_);
    }

    private ClassSearchListAdapterListener classSearchListAdapterListener;
    /**
     * Sets the listener for class click events.
     *
     * @param classSearchListAdapterListener The listener to set.
     */
    public void setClassSearchListAdapterListener(ClassSearchListAdapterListener classSearchListAdapterListener){
        this.classSearchListAdapterListener = classSearchListAdapterListener;
    }

    private Context context;
    private int layoutId;

    private List<Class> queryClassList;
    /**
     * Constructor for the ClassSearchListAdapter.
     *
     * @param context  The context in which the adapter is used.
     * @param resource The resource ID of the layout to use for each list item.
     * @param objects The list of {@link java.lang.Class} objects to display.
     */
    public ClassSearchListAdapter(@NonNull Context context, int resource, @NonNull List<Class> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.queryClassList = objects;

    }
    /**
     * This method inflates the layout for the list item, sets up the views
     * with the class information, and handles the click listener for the
     * class item.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view for the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View resultView = convertView;
        Class currentClass = getItem(position);

        if(resultView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(layoutId, null);
        }

        if(currentClass != null){
            TextView classDataTextView = resultView.findViewById(R.id.activity_class_search_list_item_class_textview);
            classDataTextView.setText(currentClass.getDepartment() + " " + currentClass.getCode() + ":  " + currentClass.getName());

            LinearLayout searchItemLayout = resultView.findViewById(R.id.activity_class_search_list_item_layout);
            searchItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    classSearchListAdapterListener.onClassClickListener(position, currentClass);
                }
            });
        }

        return resultView;
    }
    /**
     * Sets the list of classes to display.
     *
     * @param classList The new list of classes.
     */
    public void setQueryClassList(List<Class> classList){
        this.queryClassList = classList;
        notifyDataSetChanged();
    }

    /**
     * Not being used
     * @param classData
     */
    public void addClass(Class classData){
        this.queryClassList.add(classData);
        notifyDataSetChanged();
    }
    /**
     * Clears the list of classes.
     */
    public void clear(){
        this.queryClassList.clear();
        notifyDataSetChanged();
    }
}
