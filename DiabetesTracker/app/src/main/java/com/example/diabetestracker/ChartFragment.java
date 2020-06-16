package com.example.diabetestracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.core.cartesian.series.Line;
import com.anychart.charts.Cartesian;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.listeners.SetonItemSelectedListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.RecordViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment implements Observer<List<RecordTag>> {

    private AnyChartView anychart;
    private Spinner timeSpinner;
    private TextView warningTextView;


    private Set set;
    private String unit, time;

    private List<RecordTag> recordTags;

    private int selectedIndex;
    private List<DataEntry> seriesData;

    private final String MAPPING = "{ x: 'x', value: 'value' }";
    public ChartFragment() {
        // Required empty public constructor
        System.out.println("initial chart fragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        Context context = getContext();

        System.out.println("create chart fragment");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);
        time = sharedPreferences.getString(SettingsFragment.TIME_KEY, TimePickerDialogFragment.TIME_24);

        anychart = view.findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anychart);
        warningTextView = view.findViewById(R.id.warning_text);

        timeSpinner = view.findViewById(R.id.time_spinner);

        String[] times = getResources().getStringArray(R.array.chart);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item, times);
        timeSpinner.setAdapter(timeAdapter);

        timeSpinner.setOnItemSelectedListener(new SetonItemSelectedListener(this));

        RecordViewModel viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(RecordViewModel.class);

        viewModel.filterSortAsc();

        viewModel.getAllRecords().observe(getViewLifecycleOwner(), this);

        recordTags = new ArrayList<>();

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);

        cartesian.padding(10d, 20d, 20d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Biểu Đồ Chỉ Số Đường Huyết.");
        cartesian.yAxis(0).title("Chỉ Số Đường Huyết:");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        seriesData = new ArrayList<>();
        seriesData.add(new ValueDataEntry((String) null, null));

        set = Set.instantiate();
        set.data(seriesData);
        Mapping seriesMapping = set.mapAs(MAPPING);

        Line line = cartesian.line(seriesMapping);

        line.color("#ff0000");
        line.name("Chỉ Số:");
        line.hovered().markers().enabled(true);
        line.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        line.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.autoRedraw();

        anychart.setChart(cartesian);
        anychart.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
            @Override
            public void onRendered() {
                switch (selectedIndex) {
                    case 0:
                        changeAllRecord();
                        break;
                    case 1:
                        changeDay();
                        break;
                    case 2:
                        changeMonth();
                        break;
                }
            }
        });
        return view;
    }

    public void changeAllRecord() {
        seriesData = new ArrayList<>();
        for (RecordTag recordTag : recordTags) {
            BloodSugarRecord record = recordTag.getRecord();
            float glycemicIndex = record.getGlycemicIndex();

            try {
                Date dateTime = DateTimeUtil.parse(record.getRecordDate());
                String time = DateTimeUtil.formatTime24(dateTime);
                String date = DateTimeUtil.formatDate(dateTime);

                seriesData.add(new ValueDataEntry(date + "\\u000A" + time, glycemicIndex));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        set.data(seriesData);
    }


    public void triggerOnRendered() {
        anychart.getOnRenderedListener().onRendered();
    }

    public void changeDay() {
        seriesData = new ArrayList<>();
        List<String> listday = new ArrayList<>();
        for (RecordTag r : recordTags) {
            String date = null;
            try {
                Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date = DateTimeUtil.formatDate(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!listday.contains(date))
                listday.add(date);
        }
        for (String l : listday) {
            int sum = 0;
            int num = 0;
            for (RecordTag r : recordTags) {
                String date = null;
                try {
                    Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                    date = DateTimeUtil.formatDate(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (l.equals(date)) {
                    sum = sum + r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s = sum;
            if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                s = UnitConverter.mg_To_mmol((sum));
            seriesData.add(new ValueDataEntry(l , s / num));
        }
        set.data(seriesData);
    }
    public void changeMonth() {
        seriesData = new ArrayList<>();
        List<String> listday = new ArrayList<>();
        for (RecordTag r : recordTags) {
            String date = null;
            try {
                Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date = DateTimeUtil.formatMonth(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!listday.contains(date))
                listday.add(date);
        }
        for (String l : listday) {
            int sum = 0;
            int num = 0;
            for (RecordTag r : recordTags) {
                String date = null;
                try {
                    Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                    date = DateTimeUtil.formatMonth(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (l.equals(date)) {
                    sum = sum + r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s = sum;
            if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                s = UnitConverter.mg_To_mmol((sum));
            seriesData.add(new ValueDataEntry(l , s / num));
        }
        set.data(seriesData);
    }
    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
    }

    @Override
    public void onChanged(List<RecordTag> recordTags) {
        this.recordTags = recordTags;
        if (recordTags.size() > 0) {
            //make chart visible if chart is invisible
            if (anychart.getVisibility() == View.INVISIBLE) {
                anychart.setVisibility(View.VISIBLE);
                timeSpinner.setVisibility(View.VISIBLE);
                warningTextView.setVisibility(View.GONE);
            }


        } else {
            //make chart invisible and warning text visible
            warningTextView.setVisibility(View.VISIBLE);
            anychart.setVisibility(View.INVISIBLE);
            timeSpinner.setVisibility(View.INVISIBLE);
        }
    }
}
