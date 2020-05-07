package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.listeners.MainMenuItemClickListener;
import com.example.diabetestracker.listeners.NavigationOnClickListener;
import com.example.diabetestracker.listeners.TabSelectedListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        toolbar.setNavigationOnClickListener(new NavigationOnClickListener(drawerLayout, navigationView,
                getApplication()));
        toolbar.setOnMenuItemClickListener(new MainMenuItemClickListener(getSupportFragmentManager()));

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

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabSelectedListener(viewPager, toolbar));
    }
}
