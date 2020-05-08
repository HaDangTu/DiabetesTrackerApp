package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.repository.ReminderRepository;

public class MenuItemAddReminderClickListener extends BaseMenuItemClickListener {
    private ReminderRepository repository;

    public MenuItemAddReminderClickListener(AddReminderActivity activity) {
        super(activity);
        repository = new ReminderRepository(activity.getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        return false;
    }
}
