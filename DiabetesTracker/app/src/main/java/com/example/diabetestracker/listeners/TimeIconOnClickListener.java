package com.example.diabetestracker.listeners;

import android.app.Application;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TimePicker;

import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.TimePickerDialogFragment;

public class TimeIconOnClickListener extends BaseOnClickListener implements
        TimePickerDialog.OnTimeSetListener{

    private AddReminderActivity activity;

    public TimeIconOnClickListener(AddReminderActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        activity.setTime(time);
    }

    @Override
    public void onClick(View v) {
        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        dialog.setListener(this);
        dialog.show(activity.getSupportFragmentManager(), "TIME PICKER");
    }
}
