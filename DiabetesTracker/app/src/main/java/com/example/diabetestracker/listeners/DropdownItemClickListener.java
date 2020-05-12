package com.example.diabetestracker.listeners;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.AddRecordActivity;
import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.EditRecordActivity;
import com.example.diabetestracker.entities.Tag;

public class DropdownItemClickListener implements AdapterView.OnItemClickListener {
    private AppCompatActivity activity;

    public DropdownItemClickListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tag tag = (Tag) parent.getItemAtPosition(position);
        if (activity.getClass() == EditRecordActivity.class) {
            EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
            editRecordActivity.setTagId(tag.getId());
        }
        else if (activity.getClass() == AddReminderActivity.class) {
            AddReminderActivity addReminderActivity = (AddReminderActivity) activity;
            addReminderActivity.setTagId(tag.getId());
        }
        else if (activity.getClass() == AddRecordActivity.class) {
            AddRecordActivity addRecordActivity = (AddRecordActivity) activity;
            addRecordActivity.setTagId(tag.getId());
        }
    }
}
