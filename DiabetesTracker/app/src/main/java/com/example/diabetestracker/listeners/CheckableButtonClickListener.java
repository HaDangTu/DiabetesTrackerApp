package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.R;
import com.google.android.material.button.MaterialButton;

import java.util.Hashtable;

public class CheckableButtonClickListener extends BaseOnClickListener {
    private boolean isChecked;
    private Hashtable<String, Boolean> repeatDays;
    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public CheckableButtonClickListener(boolean isChecked, Hashtable<String, Boolean> repeatDays,
                                        AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
        this.isChecked = isChecked;
        this.repeatDays = repeatDays;
    }

    public CheckableButtonClickListener(boolean isChecked, Hashtable<String, Boolean> repeatDays,
                                        Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
        this.isChecked = isChecked;
        this.repeatDays = repeatDays;
    }

    @Override
    public void onClick(View v) {
        isChecked = !isChecked;
        MaterialButton button = (MaterialButton) v;
        if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;

            detailReminderFragment.setColorButton(button,
                    isChecked ? R.color.colorTextPrimary : R.color.colorPrimary,
                    isChecked ? R.color.colorPrimary : R.color.colorTextPrimary);
        }
        else if (fragment.getClass() == AddReminderFragment.class) {
            AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;

            addReminderFragment.setColorButton(button,
                    isChecked ? R.color.colorTextPrimary : R.color.colorPrimary,
                    isChecked ? R.color.colorPrimary : R.color.colorTextPrimary);
        }

        repeatDays.put(button.getTag().toString(), isChecked);
    }
}
