package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.Context;
import android.view.View;

public abstract class BaseOnClickListener implements View.OnClickListener {
    protected Application application;

    public BaseOnClickListener(Application application) {
        this.application = application;
    }
}
