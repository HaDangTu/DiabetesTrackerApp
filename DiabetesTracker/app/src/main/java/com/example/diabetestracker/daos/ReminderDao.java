package com.example.diabetestracker.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Transaction
    @Query(value = "SELECT * FROM reminders")
    LiveData<List<ReminderAndInfo>> findAll();

    @Query(value = "SELECT id FROM reminders ORDER BY id DESC LIMIT 1")
    LiveData<Integer> findLastId();
}
