package com.example.diabetestracker.listeners;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public abstract class BaseTabSelectedListener implements TabLayout.OnTabSelectedListener {
    protected ViewPager viewPager;

    public BaseTabSelectedListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
