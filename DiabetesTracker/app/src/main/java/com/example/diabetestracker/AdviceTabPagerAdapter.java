package com.example.diabetestracker;

import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class AdviceTabPagerAdapter extends FragmentStatePagerAdapter {
    private TabLayout tabLayout;

    public AdviceTabPagerAdapter(@NonNull FragmentManager fm, int behavior, TabLayout tabLayout) {
        super(fm, behavior);
        this.tabLayout = tabLayout;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EatingAdvicesFragment(tabLayout);
            case 1:
                return new GymnasticAdvicesFragment(tabLayout);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabLayout.getTabCount();
    }
}
