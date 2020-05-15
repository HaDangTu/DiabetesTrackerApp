package com.example.diabetestracker.repository;

import android.app.Application;
import android.content.Context;

import com.example.diabetestracker.ApplicationDatabase;

public abstract class BaseRepository {
    protected ApplicationDatabase database;

    public BaseRepository(Context context) {
        database = ApplicationDatabase.getInstance(context);
    }
}
