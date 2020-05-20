package com.example.diabetestracker.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.diabetestracker.AdviceActivity;
import com.example.diabetestracker.R;
import com.google.android.material.navigation.NavigationView;

/**
 * TODO add rest item click of navigation menu
 */
public class MainNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    public MainNavigationItemSelectedListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_advice:
                Intent intent = new Intent(context, AdviceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
        }
        return false;
    }
}
