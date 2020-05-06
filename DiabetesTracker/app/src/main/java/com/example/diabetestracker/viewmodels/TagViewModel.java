package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diabetestracker.repository.TagRepository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private LiveData<List<String>> tags;
    private TagRepository repository;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new TagRepository(application);
        tags = repository.findAll();
    }

    public LiveData<List<String>> getAllTags() {
        return tags;
    }
}
