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

public class ClassSearchListAdapter extends ArrayAdapter<Class> {

    public interface ClassSearchListAdapterListener{
        public void onClassClickListener(int position, Class class_);
    }

    private ClassSearchListAdapterListener classSearchListAdapterListener;

    public void setClassSearchListAdapterListener(ClassSearchListAdapterListener classSearchListAdapterListener){
        this.classSearchListAdapterListener = classSearchListAdapterListener;
    }

    private Context context;
    private int layoutId;

    private List<Class> queryClassList;

    public ClassSearchListAdapter(@NonNull Context context, int resource, @NonNull List<Class> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutId = resource;
        this.queryClassList = objects;

    }

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

    public void setQueryClassList(List<Class> classList){
        this.queryClassList = classList;
        notifyDataSetChanged();
    }

    public void addClass(Class classData){
        this.queryClassList.add(classData);
        notifyDataSetChanged();
    }

    public void clear(){
        this.queryClassList.clear();
        notifyDataSetChanged();
    }
}
