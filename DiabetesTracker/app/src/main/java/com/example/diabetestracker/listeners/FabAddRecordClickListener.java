package com.example.diabetestracker.listeners;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.R;

public class FabAddRecordClickListener extends BaseOnClickListener {

    private Fragment fragment;
    public FabAddRecordClickListener(Fragment fragment) {
        super(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, new AddRecordFragment())
                .addToBackStack("add record")
                .commit();
    }
}
