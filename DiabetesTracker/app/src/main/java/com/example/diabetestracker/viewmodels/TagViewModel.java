package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.repository.TagRepository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private LiveData<List<TagScale>> tags;
    private LiveData<List<String>> tagNames;
    private TagRepository repository;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new TagRepository(application);
        tagNames = repository.findAllTagNames();
        tags = repository.findAll();
    }

    public LiveData<List<TagScale>> getAllTag() {
        return tags;
    }

    public LiveData<List<String>> getAllTagNames() {
        return tagNames;
    }
}
