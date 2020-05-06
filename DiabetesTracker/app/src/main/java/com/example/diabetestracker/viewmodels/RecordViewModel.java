package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.repository.RecordRepository;

import java.util.List;

public class RecordViewModel extends AndroidViewModel {
    private RecordRepository repository;

    //All record in database
    private LiveData<List<RecordTag>> records;

    //Selected item
    private MutableLiveData<RecordTag> selectedRecord;

    public RecordViewModel (Application application) {
        super(application);
        repository = new RecordRepository(application);
        if (selectedRecord == null) selectedRecord = new MutableLiveData<>();
        records = repository.findAll();
    }

    public LiveData<List<RecordTag>> getAllRecords() {
        return records;
    }

    //Remove filter
    public void removeFilter() {
        records = repository.findAll();
    }
    //Filter records 7 days ago (ignore tag)
    public void filterRecords7Days() {
        records = repository.findAll7DaysAgo();
    }

    //Filter records 14 days ago (ignore tag)
    public void filterRecords14Days() {
        records = repository.findAll14DaysAgo();
    }

    //Filter records a month ago (ignore tag)
    public void filterRecordsAMonth() {
        records = repository.findAllAMonthAgo();
    }

    //Filter records 3 months ago (ignore tag)
    public void filterRecords3Months() {
        records = repository.findAll3MonthsAgo();
    }

    //Filter all records (with tags)
    public void filterWithTag(String name) {
        records = repository.findAllWithTag(name);
    }

    //Filter records 7 days ago (with tag)
    public void filterRecords7DaysWithTag(String name) {
        records = repository.findAll7DaysAgoWithTag(name);
    }

    //Filter records 14 days ago (with tag)
    public void filterRecords14DayWithTag(String name) {
        records = repository.findAll14DaysAgoWithTag(name);
    }

    //Filter records a month ago (with tag)
    public void filterRecordsAMonthWithTag(String name) {
        records = repository.findAllAMonthAgoWithTag(name);
    }

    //Filter records 3 months ago (with tag)
    public void filterRecords3MonthsWithTag(String name) {
        records = repository.findAll3MonthsAgoWithTag(name);
    }

    public void selectRecord(RecordTag record) {
        selectedRecord.setValue(record);
    }

    public MutableLiveData<RecordTag> getSelectedRecord() {
        return selectedRecord;
    }
}
