package com.example.diabetestracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.ApplicationDatabase;
import com.example.diabetestracker.daos.TagDao;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;

import java.util.List;

public class TagRepository extends BaseRepository{
    private TagDao tagDao;

    public TagRepository (Application application) {
        super(application);
        tagDao = database.tagDao();
    }

    public void insert(final Tag tag) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tagDao.insert(tag);
            }
        });
    }

    public void update(final Tag tag) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tagDao.update(tag);
            }
        });
    }

    public void delete (final Tag tag) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tagDao.delete(tag);
            }
        });
    }

    public LiveData<List<String>> findAll() {
        return tagDao.findAll();
    }

}
