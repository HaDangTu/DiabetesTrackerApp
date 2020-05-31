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
import com.example.diabetestracker.entities.TagScale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (fragment.getClass() == ChartFragment.class) {
            ChartFragment chartFragment = (ChartFragment) fragment;
            if (position == 0)
                chartFragment.ChangeSetAll();
            else if (position == 1)
                chartFragment.ChangeSetDay();
            else if (position == 2)
                chartFragment.ChangeSetMonth();
        }
    }
    public void onNothingSelected(
            AdapterView<?> adapterView) {

    }
}
