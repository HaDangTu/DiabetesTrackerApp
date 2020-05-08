package com.example.diabetestracker.listeners;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabetestracker.EditRecordActivity;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
public class EditMenuItemClickListener extends BaseMenuItemClickListener {
    private RecordRepository recordRepository;

    public EditMenuItemClickListener(AppCompatActivity  activity) {
        super(activity);
        recordRepository = new RecordRepository(activity.getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_edit:
                if (activity.getClass() == EditRecordActivity.class) {
                    EditRecordActivity editActivity = (EditRecordActivity) activity;
                    int recordId = editActivity.getRecordId();
                    int tagId = editActivity.getTagId();
                    float glycemicIndex = editActivity.getGlycemicIndex();
                    //IMPORTANT Format lại date time string trước khi (insert, update) database
                    String recordDateTime = DateTimeUtil.convertDateString(editActivity.getDateTimeRecord());
                    String note = editActivity.getNote();

                    BloodSugarRecord record = new BloodSugarRecord(recordId, glycemicIndex,
                            recordDateTime, note , tagId);

                    recordRepository.update(record);
                }
                return true;
            case R.id.item_delete:
                //TODO Add logic code to delete record
                return true;
        }
        activity.onBackPressed();
        return false;
    }
}
