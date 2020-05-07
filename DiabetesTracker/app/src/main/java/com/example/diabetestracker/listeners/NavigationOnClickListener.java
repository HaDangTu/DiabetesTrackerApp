package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NavigationOnClickListener extends BaseOnClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public NavigationOnClickListener(DrawerLayout drawerLayout, NavigationView navigationView,
                                     Application application) {
        super(application);
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
    }

    @Override
    public void onClick(View v) {
        drawerLayout.openDrawer(navigationView, true);
    }
}
