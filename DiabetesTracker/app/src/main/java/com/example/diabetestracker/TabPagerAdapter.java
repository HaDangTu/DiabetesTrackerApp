package com.example.diabetestracker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTab;
    public TabPagerAdapter(@NonNull FragmentManager fm, int behavior, int numOfTab) {
        super(fm, behavior);
        this.numOfTab = numOfTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.getInstance();
            case 1:
                return new ChartFragment();
            case 2:
                return new ReportFragment();
            case 3:
                return ReminderFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTab;
    }


}
