package com.example.diabetestracker.listeners;

import android.app.Application;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.diabetestracker.SortDialogFragment;

public class DialogCloseButtonListener extends BaseDialogButtonClickListener {

    public DialogCloseButtonListener(DialogFragment dialogFragment, Application application) {
        super(dialogFragment, application);
    }

    @Override
    public void onClick(View v) {
        dialogFragment.dismiss();
    }
}
