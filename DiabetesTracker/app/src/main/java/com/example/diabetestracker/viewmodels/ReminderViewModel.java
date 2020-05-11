package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diabetestracker.entities.ReminderAndType;
import com.example.diabetestracker.repository.ReminderRepository;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private LiveData<List<ReminderAndType>> reminders;
    private MutableLiveData<ReminderAndType> selectedItem;
    private ReminderRepository repository;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        repository = new ReminderRepository(application);
        if (reminders == null)
            reminders = repository.findAll();

        if (selectedItem == null)
            selectedItem = new MutableLiveData<>();
    }

    public void setSelectedItem(ReminderAndType reminder) {
        selectedItem.setValue(reminder);
    }

    public MutableLiveData<ReminderAndType> getSelectedItem() {
        return selectedItem;
    }

    public LiveData<List<ReminderAndType>> getAll() {
        return reminders;
    }
}
