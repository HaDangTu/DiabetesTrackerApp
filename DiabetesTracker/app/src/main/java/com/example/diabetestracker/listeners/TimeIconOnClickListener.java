package com.example.diabetestracker.listeners;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.SettingsFragment;
import com.example.diabetestracker.TimePickerDialogFragment;
import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeIconOnClickListener extends BaseOnClickListener implements
        TimePickerDialog.OnTimeSetListener {

    private Fragment fragment;
    private String timeSetting;

    public TimeIconOnClickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragment.getContext());
        timeSetting = sharedPreferences.getString(SettingsFragment.TIME_KEY,
                TimePickerDialogFragment.TIME_24);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format(Locale.US, "%d:%d", hourOfDay, minute);
        try {
            Date date = DateTimeUtil.parseTime24(time);

            if (timeSetting.equals(TimePickerDialogFragment.TIME_24)) {
                time = DateTimeUtil.formatTime24(date);
            } else
                time = DateTimeUtil.formatTime12(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            detailRecordFragment.setTime(time);
        } else if (fragment.getClass() == AddRecordFragment.class) {
            AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
            addRecordFragment.setTime(time);
        } else if (fragment.getClass() == AddReminderFragment.class) {
            AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;
            addReminderFragment.setTime(time);
            addReminderFragment.setHourOfDay(hourOfDay);
            addReminderFragment.setMinute(minute);
        } else if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;
            detailReminderFragment.setTime(time);
            detailReminderFragment.setHourOfDay(hourOfDay);
            detailReminderFragment.setMinute(minute);
        }
    }

    @Override
    public void onClick(View v) {
        TimePickerDialogFragment dialog;
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment editRecordReminder = (DetailRecordFragment) fragment;
            String time = editRecordReminder.getDateTimeRecord();
            dialog = new TimePickerDialogFragment(time);
        }
        else if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment editReminderFragment = (DetailReminderFragment) fragment;
            String time = editReminderFragment.getTime();

            try {
                Date date;
                if (timeSetting.equals(TimePickerDialogFragment.TIME_24)) {
                    date = DateTimeUtil.parseTime24(time);
                    time = DateTimeUtil.formatDateTime24(date);
                } else {
                    date = DateTimeUtil.parseTime12(time);
                    time = DateTimeUtil.formatDateTime12(date);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            dialog = new TimePickerDialogFragment(time);

        } else {
            dialog = new TimePickerDialogFragment();
        }

        dialog.setListener(this);
        dialog.show(fragment.getChildFragmentManager(), "TIME PICKER");
    }
}
