package com.example.diabetestracker.listeners;

import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.FilterDialogFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.SortDialogFragment;

public class MainMenuItemClickListener extends BaseMenuItemClickListener {
    private FragmentManager fragmentManager;

    public MainMenuItemClickListener(FragmentManager fragmentManager)
    {
        super(null);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_sort:
                SortDialogFragment sortDialog = SortDialogFragment.getInstance();
                sortDialog.show(fragmentManager, "SORT_DIALOG");
                return true;
            case R.id.item_filter:
                FilterDialogFragment filterDialog = FilterDialogFragment.getInstance();
                filterDialog.show(fragmentManager, "FILTER_DIALOG");
                return true;
            case R.id.item_select_all:
                //TODO Logic code to select all item in list
                return true;
            default:
                return false;
        }
    }
}
