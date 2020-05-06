package com.example.diabetestracker.listeners;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogButtonClickListener extends BaseOnClickListener {

    protected DialogFragment dialogFragment;
    protected Application application;

    public BaseDialogButtonClickListener(DialogFragment dialogFragment,
                                         Application application) {
        this.dialogFragment = dialogFragment;
        this.application = application;
    }
}
