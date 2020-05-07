package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.diabetestracker.AddReminderActivity;

public class FabAddReminderClickListener extends BaseOnClickListener {

    public FabAddReminderClickListener(Application application) {
        super(application);
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, AddReminderActivity.class);
        context.startActivity(intent);
    }
}
