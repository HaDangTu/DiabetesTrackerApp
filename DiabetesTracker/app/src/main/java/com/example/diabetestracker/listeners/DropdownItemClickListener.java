package com.example.diabetestracker.listeners;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.entities.Tag;

public class DropdownItemClickListener implements AdapterView.OnItemClickListener {
    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public DropdownItemClickListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    public DropdownItemClickListener(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (fragment.getClass() == AddRecordFragment.class) {
            AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
            Tag tag = (Tag) parent.getItemAtPosition(position);
            addRecordFragment.setTagId(tag.getId());
        }
        if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            Tag tag = (Tag) parent.getItemAtPosition(position);
            detailRecordFragment.setTagId(tag.getId());
        }
        else if (fragment.getClass() == AddReminderFragment.class) {
            AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;
            String type = (String) parent.getItemAtPosition(position);
            addReminderFragment.setType(type);
        }
        else if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;
            String type = (String) parent.getItemAtPosition(position);
            detailReminderFragment.setType(type);
        }

    }
}
