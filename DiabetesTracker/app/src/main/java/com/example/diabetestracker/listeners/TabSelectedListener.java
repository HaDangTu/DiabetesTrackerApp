package com.example.diabetestracker.listeners;

import android.view.Menu;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.example.diabetestracker.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class TabSelectedListener extends BaseTabSelectedListener {

    private MaterialToolbar toolbar;

    public TabSelectedListener(ViewPager viewPager, MaterialToolbar toolbar){
        super(viewPager);
        this.toolbar = toolbar;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
        setTitle(position);
        enableMainMenuItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setTitle(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle(R.string.app_name);
                break;
            case 1:
                toolbar.setTitle(R.string.tab_chart_name);
                break;
            case 2:
                toolbar.setTitle(R.string.tab_report_name);
                break;
            case 3:
                toolbar.setTitle(R.string.tab_reminder_name);
                break;
        }
    }

    private void enableMainMenuItem(int position) {
        Menu menu = toolbar.getMenu();
        MenuItem sortItem = menu.findItem(R.id.item_sort);
        MenuItem filterItem = menu.findItem(R.id.item_filter);
//        MenuItem selectAllItem = menu.findItem(R.id.item_select_all);
        switch (position) {
            case 0:
                sortItem.setVisible(true);
                filterItem.setVisible(true);
//                selectAllItem.setVisible(false);
                break;
            case 1:
                sortItem.setVisible(false);
                filterItem.setVisible(false);
//                selectAllItem.setVisible(false);
                break;
            case 2:
                sortItem.setVisible(false);
                filterItem.setVisible(false);
//                selectAllItem.setVisible(false);
                break;
            case 3:
                sortItem.setVisible(false);
                filterItem.setVisible(false);
//                selectAllItem.setVisible(true);
                break;
        }
    }
}
