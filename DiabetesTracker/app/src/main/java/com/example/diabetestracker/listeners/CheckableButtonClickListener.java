package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import com.example.diabetestracker.R;
import com.google.android.material.button.MaterialButton;

import java.util.Hashtable;

public class CheckableButtonClickListener extends BaseOnClickListener {
    private boolean isChecked;
    private MaterialButton button;
    private Hashtable<String, Boolean> repeatDays;


    public CheckableButtonClickListener(Application application, MaterialButton button, boolean isChecked,
                                        Hashtable<String, Boolean> repeatDays) {
        super(application);
        this.isChecked = isChecked;
        this.button = button;
        this.repeatDays = repeatDays;
    }

    @Override
    public void onClick(View v) {
        isChecked = !isChecked;
        if (isChecked) {
            button.setTextColor(application.getResources().getColor(R.color.colorTextPrimary));
            button.setBackgroundColor(application.getResources().getColor(R.color.colorPrimary));
        }
        else {
            button.setTextColor(application.getResources().getColor(R.color.colorPrimary));
            button.setBackgroundColor(application.getResources().getColor(R.color.colorTextPrimary));
        }

        repeatDays.put(button.getTag().toString(), isChecked);
    }
}
