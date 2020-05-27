package com.example.diabetestracker.listeners;

import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.ReportFragment;
import com.example.diabetestracker.SettingsFragment;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.util.BloodSugarComparator;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.RecordViewModel;

import java.util.Collections;
import java.util.List;

public class PeriodDialogItemClick implements DialogInterface.OnClickListener {

    private ReportFragment fragment;

    private final int TODAY = 0;
    private final int YESTERDAY = 1;
    private final int SEVEN_DAYS_AGO = 2;
    private final int A_MONTHS_AGO = 3;
    private final int THREE_MONTHS_AGO = 4;
    private final int SIX_MONTHS_AGO = 5;

    public PeriodDialogItemClick(ReportFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        RecordViewModel viewModel = new ViewModelProvider(fragment.getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication()))
                .get(RecordViewModel.class);

        //filter data by which
        switch (which) {
            case TODAY:
                viewModel.filterRecordsToday();
                break;
            case YESTERDAY:
                viewModel.filterRecordsYesterday();
                break;
            case SEVEN_DAYS_AGO:
                viewModel.filterRecords7Days();
                break;
            case A_MONTHS_AGO:
                viewModel.filterRecordsAMonth();
                break;
            case THREE_MONTHS_AGO:
                viewModel.filterRecords3Months();
                break;
            case SIX_MONTHS_AGO:
                viewModel.filterRecords6Months();
                break;
        }
        fragment.setTimeText(which);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragment.getContext());
        final String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);

        //Update UI
        viewModel.getAllRecords().observe(fragment.requireActivity(),
                new Observer<List<RecordTag>>() {
                    @Override
                    public void onChanged(List<RecordTag> recordTags) {
                        if (recordTags.size() > 0) {
                            int high = 0;
                            int normal = 0;
                            int low = 0;
                            int sum = recordTags.size();

                            //reversed because BloodSugarComparator is use for sort descending
                            BloodSugarRecord minRecord = Collections.min(recordTags,
                                    new BloodSugarComparator().reversed())
                                    .getRecord();

                            BloodSugarRecord maxRecord = Collections.max(recordTags,
                                    new BloodSugarComparator().reversed())
                                    .getRecord();

                            int sumIndexMMol = 0; //Tổng chỉ số đường huyết của các bản ghi
                            //Calculate low, normal, high
                            for (RecordTag recordTag : recordTags) {
                                BloodSugarRecord record = recordTag.getRecord();
                                Scale scale = recordTag.getTagScale().getScale();

                                float index = record.getGlycemicIndex();

                                float max = scale.getMax();
                                float min = scale.getMin();

                                sumIndexMMol += index;

                                if (index <= min) {
                                    low += 1;
                                } else if (index < max) {
                                    normal += 1;
                                } else {
                                    high += 1;
                                }
                            }

                            float percentLow = ((float) low / sum) * 100f;
                            float percentNormal = ((float) normal / sum) * 100f;
                            float percentHigh = ((float) high / sum) * 100f;
                            int eAg = sumIndexMMol / sum; //Chỉ số đường huyết trung bình
                            float hbA1c = fragment.calculateHba1c(eAg);

                            //set progress
                            fragment.setLowProgress(Math.round(percentLow));
                            fragment.setHighProgress(Math.round(percentHigh));
                            fragment.setNormalProgress(Math.round(percentNormal));

                            //set percent text
                            fragment.setLowPercentText(percentLow);
                            fragment.setNormalPercentText(percentNormal);
                            fragment.setHighPercentText(percentHigh);

                            fragment.setHba1cText(hbA1c);

                            //set max, min, average
                            int minIndex = minRecord.getGlycemicIndex();
                            int maxIndex = maxRecord.getGlycemicIndex();

                            if (unit.equals(RecordRecyclerAdapter.MMOL_L)) {
                                fragment.setMinText(UnitConverter.mg_To_mmol(minIndex));
                                fragment.setMaxText(UnitConverter.mg_To_mmol(maxIndex));
                                fragment.setAverageText(UnitConverter.mg_To_mmol(eAg));
                            } else {
                                fragment.setMinText(minIndex);
                                fragment.setMaxText(maxIndex);
                                fragment.setAverageText(eAg);
                            }

                        } else {
                            //set progress
                            fragment.setLowProgress(0);
                            fragment.setHighProgress(0);
                            fragment.setNormalProgress(0);

                            //set percent text
                            fragment.setLowPercentText(0);
                            fragment.setNormalPercentText(0);
                            fragment.setHighPercentText(0);

                            fragment.setHba1cText(0);

                            //set max, min, average
                            fragment.setMinText(0);
                            fragment.setMaxText(0);
                            fragment.setAverageText(0);
                        }
                    }

                });

        dialog.dismiss();
    }
}
