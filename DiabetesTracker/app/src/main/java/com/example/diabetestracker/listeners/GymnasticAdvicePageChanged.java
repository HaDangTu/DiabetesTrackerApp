package com.example.diabetestracker.listeners;

import androidx.fragment.app.Fragment;

import com.example.diabetestracker.GymnasticAdvicesFragment;

public class GymnasticAdvicePageChanged extends BaseOnPageChanged {

    public GymnasticAdvicePageChanged(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onPageSelected(int position) {
        GymnasticAdvicesFragment advicesFragment = (GymnasticAdvicesFragment) fragment;
        advicesFragment.setPageNum((position + 1), advicesFragment.getNumOfPage());
//        advicesFragment.setSelectedTab();
    }
}
