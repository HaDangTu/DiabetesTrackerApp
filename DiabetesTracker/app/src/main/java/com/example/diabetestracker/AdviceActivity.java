package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.diabetestracker.listeners.AdviceTabSelectedListener;
import com.example.diabetestracker.listeners.BackOnClickListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;


public class AdviceActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private AdviceViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new BackOnClickListener(this));

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab()
            .setText("Ăn uống")
            .setIcon(R.drawable.ic_restaurant), 0, true);

        tabLayout.addTab(tabLayout.newTab()
            .setText("Thể dục")
            .setIcon(R.drawable.ic_accessibility), 1);

        viewPager = findViewById(R.id.view_pager);


        AdviceTabPagerAdapter adapter = new AdviceTabPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout);

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new AdviceTabSelectedListener(viewPager));
    }
}
