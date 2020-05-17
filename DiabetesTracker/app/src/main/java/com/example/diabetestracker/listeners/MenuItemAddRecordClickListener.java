package com.example.diabetestracker.listeners;

import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;


public class MenuItemAddRecordClickListener extends BaseMenuItemClickListener {
    private RecordRepository repository;

    public MenuItemAddRecordClickListener(AddRecordFragment fragment)
    {
        super(fragment);
        repository = new RecordRepository(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;

        int id = item.getItemId();
        if (id == R.id.item_save) {
            if (!addRecordFragment.hasError()) {
                BloodSugarRecord record = new BloodSugarRecord();
                record.setBloodSugarLevel(addRecordFragment.getGlycemicIndex());
                String recordDate = DateTimeUtil.convertDateString(addRecordFragment.getDateTimeRecord());

                record.setRecordDate(recordDate);
                record.setTagId(addRecordFragment.getTagId());
                record.setNote(addRecordFragment.getNote());

                repository.insert(record);

                FragmentManager fragmentManager = fragment.getFragmentManager();
                fragmentManager.popBackStack();
            }
            return true;
        }
        return false;
    }
}
