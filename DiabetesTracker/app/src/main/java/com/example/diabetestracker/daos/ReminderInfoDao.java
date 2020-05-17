package com.example.diabetestracker.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.diabetestracker.entities.ReminderInfo;

import java.util.List;

@Dao
public interface ReminderInfoDao {

    @Insert
    void insert(ReminderInfo info);

    @Insert
    void insert(List<ReminderInfo> infos);

    @Update
    void update(ReminderInfo info);

    @Delete
    void delete(ReminderInfo info);

    @Delete
    void delete(List<ReminderInfo> infos);
}
