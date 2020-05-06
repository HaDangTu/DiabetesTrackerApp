package com.example.diabetestracker.listeners;

import android.content.Intent;
import android.view.View;

import com.example.diabetestracker.AddReminderActivity;

public class FabAddReminderClickListener extends BaseOnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddReminderActivity.class);
        v.getContext().startActivity(intent);
    }
}
