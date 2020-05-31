package com.example.diabetestracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private AnyChartView anychart;
    private Spinner timeSpinner;
    private RecordViewModel viewModel;

    private List<RecordTag> listrecord;
    private Set set;
    private int numberrow = 0;
    private String[] times;
    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        anychart = view.findViewById(R.id.any_chart_view);

        timeSpinner = view.findViewById(R.id.time_spinner);
        times = getResources().getStringArray(R.array.chart);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item, times);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    ChangeSetAll();
                else if(position==1)
                    ChangeSetDay();
                else if(position==2)
                    ChangeSetMonth();
            }
            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(RecordViewModel.class);

        listrecord = new ArrayList<RecordTag>();

        viewModel.getAllRecords().observe(getViewLifecycleOwner(), new Observer<List<RecordTag>>() {
            @Override
            public void onChanged(List<RecordTag> RecordTag) {
                listrecord.addAll(RecordTag);
            }
        });

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Biểu Đồ Chỉ Số Đường Huyết.");

        cartesian.yAxis(0).title("Chỉ Số Đường Huyết (mmol/l)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();

        seriesData.add(new CustomDataEntry(null,null));

        set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.color("#ff0000");
        series1.name("mg/dl");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        anychart.setChart(cartesian);

        set.remove(0);

        return view;
    }
    public void RemoteChart()
    {
        for(int i = 0 ; i < numberrow ; i++)
            set.remove(0);
        numberrow = 0;
    }
    public void ChangeSetAll()
    {
        RemoteChart();
        List<DataEntry> seriesData = new ArrayList<>();
        for(RecordTag r: listrecord)
        {
            String time = null, date = null;
            try {
                Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                time=DateTimeUtil.formatTime24(datetime);
                date=DateTimeUtil.formatDateMonth(datetime);
            }

            catch (ParseException e) {
                e.printStackTrace();
            }
            float s = r.getRecord().getGlycemicIndex();
            final Context context = getContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);
            //if (unit.equals(RecordRecyclerAdapter.MMOL_L))
            //    s = UnitConverter.mg_To_mmol( (int) s);
            seriesData.add(new CustomDataEntry(date + " " + time,s));
            numberrow++;
        }
        set.data(seriesData);
    }
    public void ChangeSetDay()
    {
        RemoteChart();
        List<DataEntry> seriesData = new ArrayList<>();
        List<String> listday = new ArrayList<>();
        for(RecordTag r: listrecord)
        {
            String date=null;
            try {
                Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date=DateTimeUtil.formatDateMonth(datetime);
            }

            catch (ParseException e) {
                e.printStackTrace();
            }
            if(!listday.contains(date))
                listday.add(date);
        }

        for(String l: listday)
        {
            int sum = 0;
            int num=0;
            for(RecordTag r: listrecord)
            {
                String date = null;
                try {
                    Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                    date=DateTimeUtil.formatDateMonth(datetime);
                }

                catch (ParseException e) {
                    e.printStackTrace();
                }
                if(l.equals(date))
                {
                    sum=sum+r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s;
            final Context context = getContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);
            s = UnitConverter.mg_To_mmol(sum);
            seriesData.add(new CustomDataEntry(l,(float)sum/num));
            numberrow++;
        }
        set.data(seriesData);
    }
    public void ChangeSetMonth()
    {
        RemoteChart();
        List<DataEntry> seriesData = new ArrayList<>();
        List<String> listday = new ArrayList<>();
        for(RecordTag r: listrecord)
        {
            String date=null;
            try {
                Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date=DateTimeUtil.formatMonth(datetime);
            }

            catch (ParseException e) {
                e.printStackTrace();
            }
            if(!listday.contains(date))
                listday.add(date);
        }

        for(String l: listday)
        {
            int sum = 0;
            int num=0;
            for(RecordTag r: listrecord)
            {
                String date = null;
                try {
                    Date datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                    date=DateTimeUtil.formatMonth(datetime);
                }

                catch (ParseException e) {
                    e.printStackTrace();
                }
                if(l.equals(date))
                {
                    sum=sum+r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s;
            final Context context = getContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);
            s = UnitConverter.mg_To_mmol(sum);
            seriesData.add(new CustomDataEntry(l,(float)sum/num));
            numberrow++;
        }
        set.data(seriesData);
    }

    private class CustomDataEntry extends ValueDataEntry
    {
        CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }
}
