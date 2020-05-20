package com.example.diabetestracker.listeners;


import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public abstract class BaseOnPageChanged extends ViewPager2.OnPageChangeCallback {
    protected Fragment fragment;

    public BaseOnPageChanged(Fragment fragment) {
        this.fragment = fragment;
    }
}
