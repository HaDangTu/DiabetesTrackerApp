package com.example.diabetestracker.listeners;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.TimePickerDialogFragment;

public class TimeIconOnClickListener extends BaseOnClickListener implements
        TimePickerDialog.OnTimeSetListener{


    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public TimeIconOnClickListener(AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    public TimeIconOnClickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format("%02d:%02d", hourOfDay, minute);
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            detailRecordFragment.setTime(time);
        }
        else if (fragment.getClass() == AddRecordFragment.class) {
            AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
            addRecordFragment.setTime(time);
        }
        else if (fragment.getClass() == AddReminderFragment.class) {
            AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;
            addReminderFragment.setTime(time);
            addReminderFragment.setHourOfDay(hourOfDay);
            addReminderFragment.setMinute(minute);
        }
        else if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;
            detailReminderFragment.setTime(time);
            detailReminderFragment.setHourOfDay(hourOfDay);
            detailReminderFragment.setMinute(minute);
        }
    }

    @Override
    public void onClick(View v) {
        String dateTime = "";
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment editRecordActivity = (DetailRecordFragment) fragment;
            dateTime = editRecordActivity.getDateTimeRecord();
        }

        TimePickerDialogFragment dialog = new TimePickerDialogFragment(dateTime);
        dialog.setListener(this);
        dialog.show(fragment.getFragmentManager(), "TIME PICKER");
    }
}
