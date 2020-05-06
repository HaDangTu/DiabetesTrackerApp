package com.example.diabetestracker.repository;

import android.app.Application;

import com.example.diabetestracker.ApplicationDatabase;

public abstract class BaseRepository {
    protected ApplicationDatabase database;

    public BaseRepository(Application application) {
        database = ApplicationDatabase.getInstance(application);
    }
}
