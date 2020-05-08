package com.example.diabetestracker.listeners;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseMenuItemClickListener implements Toolbar.OnMenuItemClickListener {
    protected AppCompatActivity activity;
    public BaseMenuItemClickListener(AppCompatActivity activity) {
        this.activity = activity;
    }
}
