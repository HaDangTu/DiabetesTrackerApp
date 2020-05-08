package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class FabAddRecordClickListener extends BaseOnClickListener {

    public FabAddRecordClickListener(Application application) {
        super(application);
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
//        Intent intent = new Intent(v.getContext(), AddNewRecordActivity.class);
//        context.startActivity(intent);
    }
}
