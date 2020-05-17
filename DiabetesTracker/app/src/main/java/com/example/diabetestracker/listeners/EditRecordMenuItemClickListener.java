package com.example.diabetestracker.listeners;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;

public class EditRecordMenuItemClickListener extends BaseMenuItemClickListener {
    private RecordRepository recordRepository;

    public EditRecordMenuItemClickListener(DetailRecordFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        recordRepository = new RecordRepository(fragment.getActivity().getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
        BloodSugarRecord record = detailRecordFragment.getRecord();
        switch (id) {
            case R.id.item_edit:
                if (!detailRecordFragment.hasError()) {
                    float glycemicIndex = detailRecordFragment.getGlycemicIndex();
                    String recordDateTime = DateTimeUtil.convertDateString(detailRecordFragment.getDateTimeRecord());
                    String note = detailRecordFragment.getNote();

                    record.setRecordDate(recordDateTime);
                    record.setBloodSugarLevel(glycemicIndex);
                    record.setNote(note);
                    record.setTagId(detailRecordFragment.getTagId());

                    recordRepository.update(record);
                }
                FragmentManager fragmentManager = fragment.getFragmentManager();
                fragmentManager.popBackStack();
                return true;
            case R.id.item_delete:
                recordRepository.delete(record);

                FragmentManager fragmentManager1 = fragment.getFragmentManager();
                fragmentManager1.popBackStack();
                return true;
        }

        return false;
    }
}
