package com.example.diabetestracker.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.ApplicationDatabase;
import com.example.diabetestracker.daos.ReminderDao;
import com.example.diabetestracker.daos.ReminderInfoDao;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;


import java.util.List;


public class ReminderRepository extends BaseRepository {
    private ReminderDao reminderDao;
    private ReminderInfoDao reminderInfoDao;

    public ReminderRepository(Context context) {
        super(context);
        reminderDao = database.reminderDao();
        reminderInfoDao = database.reminderInfoDao();
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

    public void insert(final ReminderInfo info) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reminderInfoDao.insert(info);
            }
        });
    }

    public void insert(final List<ReminderInfo> infos) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reminderInfoDao.insert(infos);
            }
        });
    }

    //Find all reminder in database
    public LiveData<List<ReminderAndInfo>> findAll(){
        return reminderDao.findAll();
    }

    //get previous id
    public LiveData<Integer> getPreviousId() {
        return reminderDao.findLastId();
    }
}
