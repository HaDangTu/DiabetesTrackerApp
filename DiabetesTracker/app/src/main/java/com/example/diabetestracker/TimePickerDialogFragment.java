package com.example.diabetestracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePickerDialogFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;
    private String time; //specify time for time picker

    private int hourOfDay;
    private int minute;

    public static final String TIME_12 = "12 giờ";
    public static final String TIME_24 = "24 giờ";

    public TimePickerDialogFragment() {
        time = null;
    }

    public TimePickerDialogFragment (String time) {
       this.time = time;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String timeSettings = sharedPreferences.getString(SettingsFragment.TIME_KEY, TIME_24);

        boolean flag = timeSettings.equals(TIME_24);

        if (time != null)
        try {
            //Convert lại thành kiểu yyyy-MM-dd HH:mm:ss để dễ xử lý
            if (flag) { //Kiểu 24 giờ
                time = DateTimeUtil.convertDate24(time);
            }
            else
                time = DateTimeUtil.convertDate12(time);

            Date date = DateTimeUtil.parse(time);

            calendar.setTimeInMillis(date.getTime());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(), listener, hourOfDay, minute, flag);
    }

    public void setListener(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }
}
