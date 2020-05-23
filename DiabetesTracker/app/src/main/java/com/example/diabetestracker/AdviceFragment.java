package com.example.diabetestracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetestracker.listeners.AdviceTabSelectedListener;
import com.example.diabetestracker.listeners.BackOnClickListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdviceFragment extends Fragment {

    private MaterialToolbar toolbar;
    private AdviceViewPager viewPager;
    private TabLayout tabLayout;
    
    public AdviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advice, container, false);
        
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new BackOnClickListener(this));

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab()
                .setText("Ăn uống")
                .setIcon(R.drawable.ic_restaurant), 0, true);

        tabLayout.addTab(tabLayout.newTab()
                .setText("Thể dục")
                .setIcon(R.drawable.ic_accessibility), 1);

        viewPager = view.findViewById(R.id.view_pager);


        AdviceTabPagerAdapter adapter = new AdviceTabPagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout);

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new AdviceTabSelectedListener(viewPager));
        return view;
    }
}
