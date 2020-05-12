package com.example.diabetestracker.listeners;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.AddRecordActivity;
import com.example.diabetestracker.DatePickerDialogFragment;
import com.example.diabetestracker.EditRecordActivity;

public class DateIconOnCLickListener extends BaseOnClickListener implements
        DatePickerDialog.OnDateSetListener {
    private AppCompatActivity activity;

    public DateIconOnCLickListener( AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        if (activity.getClass() == EditRecordActivity.class) {
            EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
            editRecordActivity.setDate(date);
        }
        else if (activity.getClass() == AddRecordActivity.class) {
            AddRecordActivity addRecordActivity = (AddRecordActivity) activity;
            addRecordActivity.setDate(date);
        }
    }

    @Override
    public void onClick(View v) {
        String dateTime = "";
        if (activity.getClass() == EditRecordActivity.class) {
            EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
            dateTime = editRecordActivity.getDateTimeRecord();
        }

        DatePickerDialogFragment dialog = new DatePickerDialogFragment(dateTime);
        dialog.setListener(this);
        dialog.show(activity.getSupportFragmentManager(), "DATE PICKER");
    }
}
