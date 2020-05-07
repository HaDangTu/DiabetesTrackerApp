package com.example.diabetestracker.listeners;

import android.view.MenuItem;

import com.example.diabetestracker.AddNewRecordActivity;
import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.repository.RecordRepository;


public class MenuItemAddRecordClickListener extends BaseMenuItemClickListener {
    private RecordRepository repository;
    private AddNewRecordActivity activity;

    public MenuItemAddRecordClickListener(AddNewRecordActivity activity) {
        this.activity = activity;
        repository = new RecordRepository(activity.getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //TODO logic code xử lý insert new record tại đây
        return false;
    }
}
