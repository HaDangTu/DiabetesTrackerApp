package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BackOnClickListener extends BaseOnClickListener {
    private Fragment fragment;

    public BackOnClickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
