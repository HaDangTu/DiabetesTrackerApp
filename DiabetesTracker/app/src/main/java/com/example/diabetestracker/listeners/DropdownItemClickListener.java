package com.example.diabetestracker.listeners;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.EditRecordActivity;
import com.example.diabetestracker.entities.Tag;

public class DropdownItemClickListener implements AdapterView.OnItemClickListener {
    private AppCompatActivity activity;

    public DropdownItemClickListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (activity.getClass() == EditRecordActivity.class) {
            EditRecordActivity editRecordActivity = (EditRecordActivity) activity;
            Tag tag = (Tag) parent.getItemAtPosition(position);
            editRecordActivity.setTagId(tag.getId());
        }
    }
}
