package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BackOnClickListener extends BaseOnClickListener {
    private AppCompatActivity activity;

    public BackOnClickListener(AppCompatActivity activity) {
        super(activity.getApplication());
    }

    @Override
    public void onClick(View v) {
        activity.onBackPressed();
    }
}
