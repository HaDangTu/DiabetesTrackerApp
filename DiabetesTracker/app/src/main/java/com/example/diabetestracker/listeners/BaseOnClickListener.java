package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

public abstract class BaseOnClickListener implements View.OnClickListener {
    protected Application application;

    public BaseOnClickListener(Application application) {
        this.application = application;
    }
}
