package com.example.diabetestracker.listeners;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.DatePickerDialogFragment;
import com.example.diabetestracker.DetailRecordFragment;

public class DateIconOnCLickListener extends BaseOnClickListener implements
        DatePickerDialog.OnDateSetListener {
    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public DateIconOnCLickListener( AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    public DateIconOnCLickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        if (fragment.getClass() == AddRecordFragment.class) {
            AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
            addRecordFragment.setDate(date);
        }
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            detailRecordFragment.setDate(date);
        }
        else if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            detailRecordFragment.setDate(date);
        }
    }

    @Override
    public void onClick(View v) {
        String dateTime = "";
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            dateTime = detailRecordFragment.getDateTimeRecord();
        }

        DatePickerDialogFragment dialog = new DatePickerDialogFragment(dateTime);
        dialog.setListener(this);
        dialog.show(fragment.getFragmentManager(), "DATE PICKER");
    }
}
