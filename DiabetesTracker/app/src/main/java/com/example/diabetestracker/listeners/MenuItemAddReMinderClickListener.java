package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.repository.ReminderRepository;

public class MenuItemAddReMinderClickListener extends BaseMenuItemClickListener {
    private ReminderRepository repository;
    private AddReminderActivity activity;

    public MenuItemAddReMinderClickListener(AddReminderActivity activity) {

        this.activity = activity;
        repository = new ReminderRepository(activity.getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_done) {

            activity.onBackPressed();
            return true;
        }
        return false;
    }
}
