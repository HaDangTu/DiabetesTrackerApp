package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CancelOnClickListener extends BaseOnClickListener {
    protected AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public CancelOnClickListener(AppCompatActivity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    public CancelOnClickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }
    @Override
    public void onClick(View v) {
//        activity.onBackPressed();
        FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.popBackStack();
    }
}
