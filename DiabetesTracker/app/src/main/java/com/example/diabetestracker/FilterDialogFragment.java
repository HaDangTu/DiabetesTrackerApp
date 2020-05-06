package com.example.diabetestracker;

import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.diabetestracker.listeners.DialogCloseButtonListener;
import com.example.diabetestracker.listeners.FilterDialogSaveButtonListener;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FilterDialogFragment extends DialogFragment {

    private ImageButton closeButton;
    private MaterialButton saveButton;

    private Spinner tagSpinner;
    private Spinner timeSpinner;

    private TagViewModel viewModel;

    private static FilterDialogFragment __instance = null;

    private int defaultTag;
    private int defaultTime;

    private String[] times;

    public FilterDialogFragment() {
        defaultTag = DEFAULT_TAG;
        defaultTime = DEFAULT_TIME;
    }

    public static FilterDialogFragment getInstance() {
        if (__instance == null) __instance = new FilterDialogFragment();
        return __instance;
    }

    public final int DEFAULT_TAG = 0;
    public final int DEFAULT_TIME = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitler_dialog, container, false);

        closeButton = view.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new DialogCloseButtonListener(this,
                getActivity().getApplication()));

        saveButton = view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new FilterDialogSaveButtonListener(this,
                getActivity().getApplication()));

        tagSpinner = view.findViewById(R.id.tag_spinner);
        final ArrayAdapter<String> tagAdapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item);

        viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(TagViewModel.class);


        viewModel.getAllTags().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    strings.add(0, "No tag");
                    tagAdapter.addAll(strings);
                    tagSpinner.setSelection(defaultTag);
                }
        });


        tagSpinner.setAdapter(tagAdapter);
        timeSpinner = view.findViewById(R.id.time_spinner);
        times = getResources().getStringArray(R.array.filter_names);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item, times);

        timeSpinner.setAdapter(timeAdapter);

        //Set default filter
        restoreDefault();

        return view;
    }

    public int getRecordTag() {
        return tagSpinner.getSelectedItemPosition();
    }

    public String getTagName() {
        return tagSpinner.getSelectedItem().toString();
    }

    public int getTime() {
        return timeSpinner.getSelectedItemPosition();
    }

    public void saveDefault (int tag, int time) {
        if (defaultTag != tag)
            defaultTag = tag;
        if (defaultTime != time)
            defaultTime = time;
    }

    public void restoreDefault() {

        timeSpinner.setSelection(defaultTime);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        restoreDefault();
        super.onDismiss(dialog);
    }
}
