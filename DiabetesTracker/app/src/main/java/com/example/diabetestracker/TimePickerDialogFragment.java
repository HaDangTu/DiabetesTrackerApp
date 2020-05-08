package com.example.diabetestracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePickerDialogFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;
    private String dateTime;

    private int hourOfDay;
    private int minute;

    public TimePickerDialogFragment (String dateTime) {
       this.dateTime = dateTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        try {
            dateTime = DateTimeUtil.convertDateString(dateTime);
            Date date = DateTimeUtil.parse(dateTime);
            calendar.setTimeInMillis(date.getTime());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(), listener, hourOfDay, minute, true);
    }

    public void setListener(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }
}
