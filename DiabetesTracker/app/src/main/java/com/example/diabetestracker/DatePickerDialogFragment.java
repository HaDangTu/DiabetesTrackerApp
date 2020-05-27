package com.example.diabetestracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;


    private String dateTime;

    public DatePickerDialogFragment(String dateTime) {
        this.dateTime = dateTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        try {
            if (!dateTime.equals("")) {
                dateTime = DateTimeUtil.convertDate24(dateTime);
                Date date = DateTimeUtil.parse(dateTime);
                calendar.setTimeInMillis(date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        System.out.println(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

        return new DatePickerDialog(getContext(), listener, year, monthOfYear, dayOfMonth);
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
}
