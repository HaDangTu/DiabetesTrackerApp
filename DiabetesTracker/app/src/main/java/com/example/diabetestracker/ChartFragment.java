package com.example.diabetestracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
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
import android.widget.Toast;


import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.listeners.SetonItemSelectedListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment implements Observer<List<RecordTag>> {

    private Spinner timeSpinner;
    private TextView warningTextView;
    private LineChart lineChart;

    private String unit, time;

    private List<RecordTag> recordTags;

    private int selectedIndex;
    private ArrayList<Entry> seriesData;
    private LineDataSet LineDataSet;
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

        lineChart =  view.findViewById(R.id.any_chart_view);

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
        return view;
    }
    public void Change()
    {
        XAxis xAxis = lineChart.getXAxis();
       // xAxis.setEnabled(false);
        xAxis.setLabelCount(0);
        YAxis yAxisright=lineChart.getAxisRight();
        yAxisright.setEnabled(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                switch (selectedIndex) {
                    case 0:
                        Date datetime = new Date((long)value);
                        String time = DateTimeUtil.formatTime24(datetime);
                        String date = DateTimeUtil.formatDate(datetime);
                        return time + "\n" + date;
                    case 1:
                        int day=(int)value;
                        int year = 0;
                        while( (day >= 365 && year % 4 != 3) || (day >= 366 && year % 4 == 3) )
                        {
                            year += 1;
                            if(year % 4 == 3)
                                day -= 366;
                            else
                                day -= 365;
                        }
                        int month = 1;
                        while(day > 0) {
                            switch (month) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                case 8:
                                case 10:
                                case 12:
                                    day -= 31;
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    day -= 30;
                                    break;
                                default:
                                    if (year % 4 == 0)
                                        day -= 29;
                                    else
                                        day -= 28;
                                    break;
                            }
                            month++ ;
                        }
                        month--;
                        switch (month) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                day += 31;
                                break;
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                day +=30;
                                break;
                            default:
                                if (year % 4 == 0)
                                    day += 29;
                                else
                                    day += 28;
                                break;
                        }
                        return day + "/" + month  + "/" + year;
                    case 2:
                        if(value % 12 != 0)
                            return  (int) value % 12 + 1+ "/" + ((int)value / 12 + 1);
                        else
                            return  "1/" + (int)value / 12;
                }
                return "";
            }
            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        switch (selectedIndex) {
            case 0:
                LineDataSet = new LineDataSet(changeAllRecord(),"Chỉ Số Đường Huyết");
                break;
            case 1:
                LineDataSet = new LineDataSet(changeDay(), "Chỉ Số Đường Huyết");
                break;
            case 2:
                LineDataSet = new LineDataSet(changeMonth(), "Chỉ Số Đường Huyết");
                break;
        }
        LineDataSet.setColor(Color.RED);
        LineDataSet.setDrawCircleHole(true);
        LineDataSet.setDrawCircles(true);
        LineDataSet.setCircleColor(Color.GRAY);
        ArrayList<ILineDataSet> dataset= new ArrayList<>();
        dataset.add(LineDataSet);

        LineData data=new LineData(dataset);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    public ArrayList<Entry> changeAllRecord() {
        seriesData = new ArrayList<>();
        for (RecordTag recordTag : recordTags) {
            BloodSugarRecord record = recordTag.getRecord();
            float glycemicIndex = record.getGlycemicIndex();

            try {
                Date dateTime = DateTimeUtil.parse(record.getRecordDate());
                if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                    glycemicIndex=UnitConverter.mg_To_mmol((int)glycemicIndex);
                long timestamp = dateTime.getTime();
                seriesData.add(new Entry(timestamp, glycemicIndex));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return seriesData;
    }

    public ArrayList<Entry> changeDay() {
        seriesData = new ArrayList<>();
        List<Date> listday = new ArrayList<>();
        for (RecordTag r : recordTags) {
            String date = null;
            Date datetime=null;
            try {
                datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date = DateTimeUtil.formatDate(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!listday.contains(date))
                listday.add(datetime);
        }
        for (Date l : listday) {
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
                if (DateTimeUtil.formatDate(l).equals(date)) {
                    sum = sum + r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s = sum;
            if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                s = UnitConverter.mg_To_mmol((sum));
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(l);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int num_day = 365*(year-year/4)+366*(year/4) + day;
            for(int i = 1 ; i < month; i ++)
            {
                switch (i)
                {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        num_day += 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        num_day += 30;
                        break;
                    default:
                        if(year % 4 == 0)
                            num_day += 29;
                        else
                            num_day += 28;
                        break;
                }
            }
            seriesData.add(new Entry( num_day, s / num));
        }
        return seriesData;
    }

    public ArrayList<Entry> changeMonth() {
        seriesData = new ArrayList<>();
        List<Date> listday = new ArrayList<>();
        for (RecordTag r : recordTags) {
            String date = null;
            Date datetime=null;
            try {
                datetime = DateTimeUtil.parse(r.getRecord().getRecordDate());
                date = DateTimeUtil.formatMonth(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!listday.contains(date))
                listday.add(datetime);
        }
        for (Date l : listday) {
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
                if (DateTimeUtil.formatMonth(l).equals(date)) {
                    sum = sum + r.getRecord().getGlycemicIndex();
                    num++;
                }
            }
            float s = sum;
            if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                s = UnitConverter.mg_To_mmol((sum));
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(l);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            seriesData.add(new Entry(12*(year-1) + month, s / num));
        }
        return seriesData;
    }

    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
    }

    @Override
    public void onChanged(List<RecordTag> recordTags) {
        this.recordTags = recordTags;
        Change();
        if (recordTags.size() > 0) {
            //make chart visible if chart is invisible
            if (lineChart.getVisibility() == View.INVISIBLE) {
                lineChart.setVisibility(View.VISIBLE);
                timeSpinner.setVisibility(View.VISIBLE);
                warningTextView.setVisibility(View.GONE);
            }
        } else {
            //make chart invisible and warning text visible
            warningTextView.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.INVISIBLE);
            timeSpinner.setVisibility(View.INVISIBLE);
        }
    }
}
