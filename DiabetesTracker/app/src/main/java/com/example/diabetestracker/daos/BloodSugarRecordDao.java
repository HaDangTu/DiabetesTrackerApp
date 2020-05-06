package com.example.diabetestracker.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;

import java.util.List;

@Dao
public interface BloodSugarRecordDao {
    @Insert
    long insert(BloodSugarRecord record);

    @Update
    void update(BloodSugarRecord record);

    @Delete
    void delete(BloodSugarRecord record);

    //findAll record (ignore tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records")
    LiveData<List<RecordTag>> findAll();

    //find all records 7 days ago (ignore tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-7 days')")
    LiveData<List<RecordTag>> findAll7DaysAgo();

    //find all records 14 days ago (ignore tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-14 days')")
    LiveData<List<RecordTag>> findAll14DaysAgo();

    //find all records a month ago (ignore tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-1 months')")
    LiveData<List<RecordTag>> findAllAMonthAgo();

    //find all records 3 months ago (ignore tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-3 months')")
    LiveData<List<RecordTag>> findAll3MonthsAgo();

    //findAll record (with tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records " +
            "WHERE tag_id = (SELECT id FROM tags WHERE name = :name)")
    LiveData<List<RecordTag>> findAllWithTag(String name);

    //find all records 7 days ago (with tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-7 days')" +
            "AND tag_id = (SELECT id FROM tags WHERE name = :name)")
    LiveData<List<RecordTag>> findAll7DaysAgoWithTag(String name);

    //find all records 14 days ago (with tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-14 days')" +
            "AND tag_id = (SELECT id FROM tags WHERE name = :name)")
    LiveData<List<RecordTag>> findAll14DaysAgoWithTag(String name);

    //find all records a month ago (with tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-1 months')" +
            "AND tag_id = (SELECT id FROM tags WHERE name = :name)")
    LiveData<List<RecordTag>> findAllAMonthAgoWithTag(String name);

    //find all records 3 months ago (with tag)
    @Transaction
    @Query(value = "SELECT * FROM blood_sugar_records WHERE DATE(record_date) >= DATE('now', '-3 months')" +
            "AND tag_id = (SELECT id FROM tags WHERE name = :name)")
    LiveData<List<RecordTag>> findAll3MonthsAgoWithTag(String name);
    
}
