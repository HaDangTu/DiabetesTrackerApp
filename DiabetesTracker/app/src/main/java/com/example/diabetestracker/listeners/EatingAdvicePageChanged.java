package com.example.diabetestracker.listeners;


import androidx.fragment.app.Fragment;

import com.example.diabetestracker.EatingAdvicesFragment;

public class EatingAdvicePageChanged extends BaseOnPageChanged {

    public EatingAdvicePageChanged(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onPageSelected(int position) {
        EatingAdvicesFragment advicesFragment = (EatingAdvicesFragment) fragment;
        advicesFragment.setPageNum((position + 1), advicesFragment.getNumOfPage());
//        advicesFragment.setSelectedTab();
    }
}
