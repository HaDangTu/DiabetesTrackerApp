package com.example.diabetestracker.listeners;

import android.app.Application;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.EditRecordActivity;
import com.example.diabetestracker.TimePickerDialogFragment;

public class TimeIconOnClickListener extends BaseOnClickListener implements
        TimePickerDialog.OnTimeSetListener{

    private AppCompatActivity activity;

    public TimeIconOnClickListener(AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format("%02d:%02d", hourOfDay, minute);
        if (activity.getClass() == AddReminderActivity.class) {
            AddReminderActivity reminderActivity = (AddReminderActivity) activity;
            reminderActivity.setTime(time);
        }
        else if (activity.getClass() == EditRecordActivity.class) {
            EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
            editRecordActivity.setTime(time);
        }
    }

    @Override
    public void onClick(View v) {
        EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
        String dateTime = editRecordActivity.getDateTimeRecord();
        TimePickerDialogFragment dialog = new TimePickerDialogFragment(dateTime);
        dialog.setListener(this);
        dialog.show(activity.getSupportFragmentManager(), "TIME PICKER");
    }
}
