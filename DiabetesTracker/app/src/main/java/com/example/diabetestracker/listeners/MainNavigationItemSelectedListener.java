package com.example.diabetestracker.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.AdviceFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.SearchFoodGIFragment;
import com.example.diabetestracker.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;
    private Context context;

    public MainNavigationItemSelectedListener(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean result = false;
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        switch (itemId) {
            case R.id.item_add_record:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, new AddRecordFragment())
                        .addToBackStack("add new record")
                        .commit();
                result = true;
                break;
            case R.id.item_search_gi:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, new SearchFoodGIFragment())
                        .addToBackStack("search food gi")
                        .commit();
                result = true;
                break;
            case R.id.item_remind:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, new AddReminderFragment())
                        .addToBackStack("add new reminder")
                        .commit();
                result = true;
                break;
            case R.id.item_advice:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, new AdviceFragment())
                        .addToBackStack("advice")
                        .commit();
                result = true;
                break;
            case R.id.item_settings:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, new SettingsFragment())
                        .addToBackStack("settins")
                        .commit();
                result = true;
                break;
            case R.id.item_exit_app:
                fragment.getActivity().finish();
                result = true;
                break;
        }
        return result;
    }
}
