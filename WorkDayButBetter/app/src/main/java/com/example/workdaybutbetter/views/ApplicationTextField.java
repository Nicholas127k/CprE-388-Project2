package com.example.workdaybutbetter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.workdaybutbetter.R;

public class ApplicationTextField extends LinearLayout {

    private final EditText editText;
    private final ImageView iconView;

    public ApplicationTextField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        editText = findViewById(R.id.application_text_field_edit_text);
        iconView = findViewById(R.id.application_text_field_icon);

        try (TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ApplicationTextField)) {
            iconView.setImageDrawable(attributes.getDrawable(R.styleable.ApplicationTextField_icon));
            editText.setHint(attributes.getString(R.styleable.ApplicationTextField_hint));
            attributes.recycle();
        }

        inflate(getContext(), R.layout.application_text_field, this);

    }

    public String getText(){
        return editText.getText().toString();
    }
}
