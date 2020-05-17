package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.ReminderFragment;
import com.example.diabetestracker.ReminderRecyclerAdapter;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.viewmodels.ReminderViewModel;

public class ReminderItemClickListener implements ReminderRecyclerAdapter.OnReminderClickListener {
    private Application application;
    private ReminderFragment fragment;

    public ReminderItemClickListener(ReminderFragment fragment) {
        this.application = fragment.getActivity().getApplication();
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v, ReminderAndInfo reminderAndInfo) {
        ReminderViewModel reminderViewModel = new ViewModelProvider(fragment.requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ReminderViewModel.class);

        reminderViewModel.setSelectedItem(reminderAndInfo);

        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, new DetailReminderFragment())
                .addToBackStack("Detail fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
