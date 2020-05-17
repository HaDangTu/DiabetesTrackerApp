package com.example.diabetestracker.listeners;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public abstract class BaseMenuItemClickListener implements Toolbar.OnMenuItemClickListener {
    protected Fragment fragment;

    public BaseMenuItemClickListener(Fragment fragment) {
        this.fragment = fragment;
    }
}
