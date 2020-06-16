package com.example.diabetestracker.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.ApplicationDatabase;
import com.example.diabetestracker.daos.BloodSugarRecordDao;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;

import java.util.List;

public class RecordRepository extends BaseRepository {
    private BloodSugarRecordDao recordDao;

    public RecordRepository(Application application){
        super(application);
        recordDao = database.recordDao();
    }

    public void insert (final BloodSugarRecord record) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recordDao.insert(record);
            }
        });
    }

    public void update (final BloodSugarRecord record) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recordDao.update(record);
            }
        });
    }

    public void delete (final BloodSugarRecord record) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recordDao.delete(record);
            }
        });
    }

    public void deleteAll() {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recordDao.deleteAll();
            }
        });
    }
    //findAll record (ignore tag)
    public LiveData<List<RecordTag>> findAll(){
        return recordDao.findAll();
    }
    //find All record sort ascending (ignore tag)
    public LiveData<List<RecordTag>> findAllSortAsc(){
        return recordDao.findAllSortAsc();
    }

    //find all records 7 days ago (ignore tag)
    public LiveData<List<RecordTag>> findAll7DaysAgo() {
        return recordDao.findAll7DaysAgo();
    }

    //find all records 14 days ago (ignore tag)
    public LiveData<List<RecordTag>> findAll14DaysAgo() {
        return recordDao.findAll14DaysAgo();
    }

    //find all records a month ago (ignore tag)
    public LiveData<List<RecordTag>> findAllAMonthAgo() {
        return recordDao.findAllAMonthAgo();
    };

    //find all records 3 months ago (ignore tag)
    public LiveData<List<RecordTag>> findAll3MonthsAgo() {
        return recordDao.findAll3MonthsAgo();
    }

    //findAll record (with tag)
    public LiveData<List<RecordTag>> findAllWithTag(String name) {
        return recordDao.findAllWithTag(name);
    }

    //find all records 7 days ago (with tag)
    public LiveData<List<RecordTag>> findAll7DaysAgoWithTag(String name) {
        return recordDao.findAll7DaysAgoWithTag(name);
    }

    //find all records 14 days ago (with tag)
    public LiveData<List<RecordTag>> findAll14DaysAgoWithTag(String name) {
        return recordDao.findAll14DaysAgoWithTag(name);
    }

    //find all records a month ago (with tag)
    public LiveData<List<RecordTag>> findAllAMonthAgoWithTag(String name) {
        return recordDao.findAllAMonthAgoWithTag(name);
    }

    //find all records 3 months ago (with tag)
    public LiveData<List<RecordTag>> findAll3MonthsAgoWithTag(String name) {
        return recordDao.findAll3MonthsAgoWithTag(name);
    }

    //find all records today
    public LiveData<List<RecordTag>> findAllToday() {
        return recordDao.findAllRecordsToday();
    }

    //find all records yesterday
    public LiveData<List<RecordTag>> findAllYesterday() {
        return recordDao.findAllYesterday();
    }

    //find all records 6 months ago (ignore tag)
    public LiveData<List<RecordTag>> findAll6MonthsAgo(){
        return recordDao.findAll6MonthsAgo();
    }
}
