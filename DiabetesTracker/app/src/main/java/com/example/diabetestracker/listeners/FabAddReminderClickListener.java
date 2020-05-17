package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.R;

public class FabAddReminderClickListener extends BaseOnClickListener {
    private FragmentManager fragmentManager;

    public FabAddReminderClickListener(FragmentManager fragmentManager) {
        super(null);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, new AddReminderFragment())
                .addToBackStack("Add reminder")
                .commit();
    }
}
