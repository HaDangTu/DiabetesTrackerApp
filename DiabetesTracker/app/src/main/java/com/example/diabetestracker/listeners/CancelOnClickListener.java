package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CancelOnClickListener extends BaseOnClickListener {
    protected AppCompatActivity activity;

    public CancelOnClickListener(AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        activity.onBackPressed();
    }
}
