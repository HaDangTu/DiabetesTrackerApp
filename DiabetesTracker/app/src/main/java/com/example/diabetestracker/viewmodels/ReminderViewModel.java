package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diabetestracker.entities.ReminderTag;
import com.example.diabetestracker.repository.ReminderRepository;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private LiveData<List<ReminderTag>> reminders;
    private MutableLiveData<ReminderTag> selectedItem;
    private ReminderRepository repository;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        repository = new ReminderRepository(application);
        if (reminders == null)
            reminders = repository.findAll();

        if (selectedItem == null)
            selectedItem = new MutableLiveData<>();
    }

    public void setSelectedItem(ReminderTag reminder) {
        selectedItem.setValue(reminder);
    }

    public MutableLiveData<ReminderTag> getSelectedItem() {
        return selectedItem;
    }

    public LiveData<List<ReminderTag>> getAll() {
        return reminders;
    }
}
