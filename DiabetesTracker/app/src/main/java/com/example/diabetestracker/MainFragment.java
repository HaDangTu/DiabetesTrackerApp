package com.example.diabetestracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetestracker.listeners.MainMenuItemClickListener;
import com.example.diabetestracker.listeners.MainNavigationItemSelectedListener;
import com.example.diabetestracker.listeners.NavigationOnClickListener;
import com.example.diabetestracker.listeners.TabSelectedListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        navigationView = view.findViewById(R.id.navigation_view);
        drawerLayout = view.findViewById(R.id.drawer);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        navigationView.setNavigationItemSelectedListener(new MainNavigationItemSelectedListener(getContext()));

        toolbar.setNavigationOnClickListener(new NavigationOnClickListener(drawerLayout, navigationView,
                getActivity().getApplication()));
        toolbar.setOnMenuItemClickListener(new MainMenuItemClickListener(getFragmentManager()));

        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.tab_home_name)
                .setIcon(R.drawable.ic_action_home), 0, true);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.tab_chart_name)
                .setIcon(R.drawable.ic_action_show_chart), 1);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.tab_report_name)
                .setIcon(R.drawable.ic_bar_chart), 2);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.tab_reminder_name)
                .setIcon(R.drawable.ic_action_reminder), 3);

        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabSelectedListener(viewPager, toolbar));
        return view;
    }
}
