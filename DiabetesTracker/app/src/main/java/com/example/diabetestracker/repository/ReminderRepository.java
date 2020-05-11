package com.example.diabetestracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.ApplicationDatabase;
import com.example.diabetestracker.daos.ReminderDao;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndType;

import java.util.List;


public class ReminderRepository extends BaseRepository {
    private ReminderDao reminderDao;

    public ReminderRepository(Application application) {
        super(application);
        reminderDao = database.reminderDao();
    }

    public void insert(final Reminder reminder) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reminderDao.insert(reminder);
            }
        });
    }

    public void update(final Reminder reminder) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reminderDao.update(reminder);
            }
        });
    }

    public void delete(final Reminder reminder) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reminderDao.delete(reminder);
            }
        });
    }

    //Find all reminder in database
    public LiveData<List<ReminderAndType>> findAll(){
        return reminderDao.findAll();
    }
}
