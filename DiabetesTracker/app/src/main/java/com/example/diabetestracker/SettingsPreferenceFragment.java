package com.example.diabetestracker;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.diabetestracker.listeners.DeleteDataPreferenceClickListener;

public class SettingsPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        ListPreference unitPreference = findPreference("unit");
        unitPreference.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());
//        unitPreference.setValueIndex(0);

        ListPreference timePreference = findPreference("time");
        timePreference.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());
//        timePreference.setValueIndex(0);

        Preference deleteData = findPreference("delete_all_data");
        deleteData.setOnPreferenceClickListener(new DeleteDataPreferenceClickListener());
    }
}
