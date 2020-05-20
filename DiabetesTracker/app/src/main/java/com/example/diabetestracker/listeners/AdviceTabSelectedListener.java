package com.example.diabetestracker.listeners;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class AdviceTabSelectedListener extends BaseTabSelectedListener {

    public AdviceTabSelectedListener(ViewPager viewPager) {
        super(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        System.out.println("Tab selected: " + tab.getPosition());
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
