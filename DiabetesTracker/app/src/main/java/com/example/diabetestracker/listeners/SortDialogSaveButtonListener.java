package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.Observer;

import com.example.diabetestracker.HomeFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.SortDialogFragment;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.util.BloodSugarComparator;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.RecordViewModel;

import java.util.Collections;
import java.util.List;


public class SortDialogSaveButtonListener extends BaseDialogButtonClickListener {

    public SortDialogSaveButtonListener(SortDialogFragment dialogFragment,
                                        Application application) {
        super(dialogFragment, application);
    }

    public void sort(HomeFragment fragment, final int sortContent, final int sortType) {
        RecordViewModel viewModel = fragment.getViewModel();
        final RecordRecyclerAdapter adapter = fragment.getAdapter();

        final DateTimeUtil.DateComparator dateComparator = new DateTimeUtil.DateComparator();
        final BloodSugarComparator bloodComparator = new BloodSugarComparator();

        viewModel.getAllRecords().observe(fragment.getViewLifecycleOwner(),
                new Observer<List<RecordTag>>() {
                    @Override
                    public void onChanged(List<RecordTag> recordTags) {
                        switch (sortContent) {
                            case R.id.radio_date:
                                switch (sortType) {
                                    case R.id.radio_descending:
                                        //Sắp xếp record giảm dần theo ngày
                                        Collections.sort(recordTags, dateComparator);
                                        break;
                                    case R.id.radio_ascending:
                                        //Sắp xếp record tăng dần theo ngày
                                        Collections.sort(recordTags, dateComparator.reversed());
                                        break;
                                }
                                break;
                            case R.id.radio_blood_sugar_level:
                                switch (sortType) {
                                    case R.id.radio_descending:
                                        //Sắp xếp record giảm dần theo chỉ số đường huyết
                                        Collections.sort(recordTags, bloodComparator);
                                        break;
                                    case R.id.radio_ascending:
                                        //Sắp xếp record tăng dần theo chỉ số đường huyết
                                        Collections.sort(recordTags, bloodComparator.reversed());
                                        break;
                                }
                                break;
                        }
                        adapter.setRecords(recordTags);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        SortDialogFragment dialog = (SortDialogFragment) dialogFragment;
        HomeFragment homeFragment = HomeFragment.getInstance();

        final int sortContent = dialog.getSortContentCheck();
        final int sortType = dialog.getSortTypeCheck();

        sort(homeFragment, sortContent, sortType);

        dialog.saveDefault(sortContent, sortType);
        dialog.dismiss();
    }
}
