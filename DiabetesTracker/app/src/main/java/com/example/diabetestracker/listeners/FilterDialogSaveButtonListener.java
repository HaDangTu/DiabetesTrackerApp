package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.example.diabetestracker.FilterDialogFragment;
import com.example.diabetestracker.HomeFragment;
import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.SortDialogFragment;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.viewmodels.RecordViewModel;

import java.util.List;

public class FilterDialogSaveButtonListener extends BaseDialogButtonClickListener {

    public FilterDialogSaveButtonListener(DialogFragment dialogFragment, Application application) {
        super(dialogFragment, application);
    }

    @Override
    public void onClick(View v) {
        final FilterDialogFragment dialog = (FilterDialogFragment) dialogFragment;
        final int tag = dialog.getRecordTag();
        final int time = dialog.getTime();

        HomeFragment homeFragment = HomeFragment.getInstance();
        final RecordRecyclerAdapter adapter = homeFragment.getAdapter();

        final RecordViewModel viewModel = homeFragment.getViewModel();

        String tagName = dialog.getTagName();

        //Filter
        if (tag == dialog.DEFAULT_TAG) {
            if (time == 0){
                viewModel.removeFilter();
            }
            else if (time == 1) {
                viewModel.filterRecords7Days();
            }
            else if (time == 2) {
                viewModel.filterRecords14Days();
            }
            else if (time == 3) {
                viewModel.filterRecordsAMonth();
            }
            else if (time == 4) {
                viewModel.filterRecords3Months();
            }
        }
        else {
            if (time == 0) {
                viewModel.filterWithTag(tagName);
            }
            else if (time == 1) {
                viewModel.filterRecords7DaysWithTag(tagName);
            }
            else if (time == 2) {
                viewModel.filterRecordsAMonthWithTag(tagName);
            }
            else if (time == 3) {
                viewModel.filterRecordsAMonthWithTag(tagName);
            }
            else if (time == 4) {
                viewModel.filterRecords3MonthsWithTag(tagName);
            }
        }
        SortDialogFragment dialogFragment = SortDialogFragment.getInstance();
        SortDialogSaveButtonListener listener = new SortDialogSaveButtonListener(
                dialogFragment, application);

        int sortContent = dialogFragment.getDefaultSortContent();
        int sortType = dialogFragment.getDefaultSortType();

        listener.sort(homeFragment, sortContent, sortType);


//        viewModel.getAllRecords().observe(homeFragment.getViewLifecycleOwner(), new
//                Observer<List<RecordTag>>() {
//                    @Override
//                    public void onChanged(List<RecordTag> recordTags) {
//
//
//                        adapter.setRecords(recordTags);
//                    }
//                });

        //Save filter
        dialog.saveDefault(tag, time);
        dialog.dismiss();
    }
}
