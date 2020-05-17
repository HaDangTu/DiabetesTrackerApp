package com.example.diabetestracker.listeners;

import android.app.Application;

import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogButtonClickListener extends BaseOnClickListener {

    protected DialogFragment dialogFragment;

    public BaseDialogButtonClickListener(DialogFragment dialogFragment,
                                         Application application) {
        super (application);
        this.dialogFragment = dialogFragment;
    }
}
