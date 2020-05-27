package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.example.diabetestracker.R;
import com.example.diabetestracker.repository.RecordRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;



public class DeleteDataPreferenceClickListener implements Preference.OnPreferenceClickListener {
    private Fragment fragment;

    public DeleteDataPreferenceClickListener(Fragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragment.getContext());
        builder.setTitle("Warning")
                .setMessage(R.string.warning_delete_data)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Application application = fragment.getActivity().getApplication();
                        final RecordRepository repository = new RecordRepository(application);
                        repository.deleteAll();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}
