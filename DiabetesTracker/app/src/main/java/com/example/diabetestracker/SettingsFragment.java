package com.example.diabetestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.listeners.BackOnClickListener;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingsFragment extends Fragment {

    private MaterialToolbar toolbar;

    public static final String UNIT_KEY = "unit";
    public static final String TIME_KEY = "time";

    public SettingsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new BackOnClickListener(this));

        return view;
    }
}
