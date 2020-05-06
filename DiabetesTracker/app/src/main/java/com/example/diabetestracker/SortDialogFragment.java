package com.example.diabetestracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diabetestracker.listeners.DialogCloseButtonListener;
import com.example.diabetestracker.listeners.SortDialogSaveButtonListener;
import com.google.android.material.button.MaterialButton;

public class SortDialogFragment extends DialogFragment {

    private RadioGroup sortContent;
    private RadioGroup sortType;

    private MaterialButton saveButton;
    private ImageButton closeButton;

    private final String SORT_TYPE_KEY = "SORT TYPE";
    private final String SORT_CONTENT_KEY = "SORT CONTENT";

    private static SortDialogFragment __instance = null;

    public static SortDialogFragment getInstance() {
        if (__instance == null) __instance = new SortDialogFragment();
        return __instance;
    }

    private int defaultSortContent;
    private int defaultSortType;

    public SortDialogFragment() {
        defaultSortContent = R.id.radio_date;
        defaultSortType = R.id.radio_descending;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_dialog, container, false);

        closeButton = view.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new DialogCloseButtonListener(
                this, getActivity().getApplication()));

        sortContent = view.findViewById(R.id.radio_sort_content);

        sortType = view.findViewById(R.id.radio_sort_type);


        if (savedInstanceState != null) {
            sortContent.check(savedInstanceState.getInt(SORT_CONTENT_KEY));
            sortType.check(savedInstanceState.getInt(SORT_TYPE_KEY));
        }


        saveButton = view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new SortDialogSaveButtonListener(
                this, getActivity().getApplication()));
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        restoreDefault();
        super.onDismiss(dialog);
    }

    public void restoreDefault() {
        sortContent.check(defaultSortContent);
        sortType.check(defaultSortType);
    }

    public int getSortContentCheck() {
        return sortContent.getCheckedRadioButtonId();
    }

    public int getSortTypeCheck() {
        return sortType.getCheckedRadioButtonId();
    }

    public void saveDefault(int defaultSortContent, int defaultSortType) {
        if (this.defaultSortContent != defaultSortContent)
            this.defaultSortContent = defaultSortContent;

        if (this.defaultSortType != defaultSortType)
            this.defaultSortType = defaultSortType;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SORT_CONTENT_KEY, getSortContentCheck());
        outState.putInt(SORT_TYPE_KEY, getSortTypeCheck());
        super.onSaveInstanceState(outState);
    }

    public int getDefaultSortContent() {
        return defaultSortContent;
    }

    public int getDefaultSortType() {
        return defaultSortType;
    }

}
