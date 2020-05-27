package com.example.diabetestracker;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.listeners.PeriodDialogItemClick;
import com.example.diabetestracker.util.BloodSugarComparator;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private MaterialTextView timeTextView;

    private MaterialTextView lowPercentTextView;
    private MaterialTextView normalPercentTextView;
    private MaterialTextView highPercentTextView;

    private MaterialTextView minTextView;
    private MaterialTextView averageTextView;
    private MaterialTextView maxTextView;

    private MaterialTextView hba1cTextView;

    private ProgressBar lowProgressBar;
    private ProgressBar normalProgressBar;
    private ProgressBar highProgressBar;

    private AlertDialog periodDialog;
    private String unit;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);

        timeTextView = view.findViewById(R.id.time_textview);
        lowPercentTextView = view.findViewById(R.id.low_percent_textview);
        normalPercentTextView = view.findViewById(R.id.normal_percent_textview);
        highPercentTextView = view.findViewById(R.id.high_percent_textview);

        minTextView = view.findViewById(R.id.min_textview);
        maxTextView = view.findViewById(R.id.max_textview);
        averageTextView = view.findViewById(R.id.average_textview);

        hba1cTextView = view.findViewById(R.id.hba1c_textview);

        lowProgressBar = view.findViewById(R.id.low_progressbar);
        normalProgressBar = view.findViewById(R.id.normal_progressbar);
        highProgressBar = view.findViewById(R.id.high_progressbar);

        periodDialog = createPeriodDialog();

        MaterialButton btnSelect = view.findViewById(R.id.btn_select_period);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodDialog.show();
            }
        });

        RecordViewModel viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(RecordViewModel.class);

        viewModel.filterRecordsToday();
        setTimeText(0);

        viewModel.getAllRecords().observe(getViewLifecycleOwner(),
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
                            float hbA1c = calculateHba1c(eAg);

                            //set progress
                            setLowProgress(Math.round(percentLow));
                            setHighProgress(Math.round(percentHigh));
                            setNormalProgress(Math.round(percentNormal));

                            //set percent text
                            setLowPercentText(percentLow);
                            setNormalPercentText(percentNormal);
                            setHighPercentText(percentHigh);

                            setHba1cText(hbA1c);

                            //set max, min, average
                            int minIndex = minRecord.getGlycemicIndex();
                            int maxIndex = maxRecord.getGlycemicIndex();

                            if (unit.equals(RecordRecyclerAdapter.MMOL_L)) {
                                setMinText(UnitConverter.mg_To_mmol(minIndex));
                                setMaxText(UnitConverter.mg_To_mmol(maxIndex));
                                setAverageText(UnitConverter.mg_To_mmol(eAg));
                            } else {
                                setMinText(minIndex);
                                setMaxText(maxIndex);
                                setAverageText(eAg);
                            }

                        } else {
                            //set progress
                            setLowProgress(0);
                            setHighProgress(0);
                            setNormalProgress(0);

                            //set percent text
                            setLowPercentText(0);
                            setNormalPercentText(0);
                            setHighPercentText(0);

                            setHba1cText(0);

                            //set max, min, average
                            setMinText(0);
                            setMaxText(0);
                            setAverageText(0);
                        }
                    }

                });
        return view;
    }

    public void setTimeText(int period) {
        String[] times = getResources().getStringArray(R.array.periods);

        timeTextView.setText(times[period]);
    }

    public void setMinText(float min) {
        if (unit.equals(RecordRecyclerAdapter.MG_DL))
            minTextView.setText(getString(R.string.min_mg, (int) min));
        else
            minTextView.setText(getString(R.string.min_mmol, min));
    }

    public void setMaxText(float max) {
        if (unit.equals(RecordRecyclerAdapter.MG_DL))
            maxTextView.setText(getString(R.string.max_mg, (int) max));
        else
            maxTextView.setText(getString(R.string.max_mmol, max));
    }

    public void setAverageText(float average) {
        if (unit.equals(RecordRecyclerAdapter.MG_DL))
            averageTextView.setText(getString(R.string.average_mg, (int) average));
        else
            averageTextView.setText(getString(R.string.average_mmol, average));
    }

    public void setHba1cText(float hba1c) {
        hba1cTextView.setText(getString(R.string.hba1c, hba1c));
    }

    public void setLowPercentText(float percent) {
        lowPercentTextView.setText(getString(R.string.percent, percent));
    }

    public void setNormalPercentText(float percent) {
        normalPercentTextView.setText(getString(R.string.percent, percent));
    }

    public void setHighPercentText(float percent) {
        highPercentTextView.setText(getString(R.string.percent, percent));
    }

    public void setLowProgress(int progress) {
        lowProgressBar.setProgress(progress);
    }

    public void setHighProgress(int progress) {
        highProgressBar.setProgress(progress);
    }

    public void setNormalProgress(int progress) {
        normalProgressBar.setProgress(progress);
    }

    private AlertDialog createPeriodDialog() {
        View customTitle = LayoutInflater.from(getContext()).inflate(R.layout.dialog_title,
                null, false);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        return builder.setCustomTitle(customTitle)
                .setSingleChoiceItems(R.array.periods, 0, new PeriodDialogItemClick(this))
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    /**
     * Tính chỉ số Hba1c (đơn vị mg/dL)
     *
     * @param eAg chỉ số đường huyết trung bình trong khoảng thời gian cho trước
     * @return Hba1c
     */
    public float calculateHba1c(float eAg) {
        return (eAg + 46.7f) / 28.7f;
    }

}
