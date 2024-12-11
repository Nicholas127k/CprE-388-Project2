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

public class RemoveFromSignUpQueueAdapter extends ArrayAdapter<Class> {

    private Context context;
    private int layoutId;
    private List<Class> classData;

    public RemoveFromSignUpQueueAdapter(@NonNull Context context, int resource, @NonNull List<Class> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.classData = objects;
    }

    public interface OnClickListener{
        public void onClick(View view, Class class_);
    }

    private OnClickListener onClickListener;

    public void setOnButtonClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

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

    public void setSignUpQueueData(List<Class> classes){
        List<Class> dataCopy = new ArrayList<>(classes);
        this.clear();
        this.classData = dataCopy;
        this.addAll(this.classData);
        notifyDataSetChanged();
    }
}
