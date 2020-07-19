package com.example.diabetestracker.listeners;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.ChartFragment;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.SearchFoodGIFragment;
import com.example.diabetestracker.entities.FoodAndType;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.viewmodels.RecordViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class SetonItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public SetonItemSelectedListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    public SetonItemSelectedListener(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        if (fragment.getClass() == ChartFragment.class) {
            final ChartFragment chartFragment = (ChartFragment) fragment;
            chartFragment.setSelectedIndex(position);
            chartFragment.Change();
        }
    }

    public void onNothingSelected(
            AdapterView<?> adapterView) {

    }
}
