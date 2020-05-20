package com.example.diabetestracker.viewmodels;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diabetestracker.entities.AdviceAndType;
import com.example.diabetestracker.repository.AdviceRepository;

import java.util.List;

public class AdviceViewModel extends AndroidViewModel {

    private LiveData<List<AdviceAndType>> eatingAdvices;
    private LiveData<List<AdviceAndType>> gymnasticAdvices;

    private AdviceRepository repository;

    public AdviceViewModel(@NonNull Application application) {
        super(application);

        repository = new AdviceRepository(application);

        if (eatingAdvices == null) {
            eatingAdvices = repository.findAllEatingAdvices();
        }

        if (gymnasticAdvices == null) {
            gymnasticAdvices = repository.findAllGymnasticAdvices();
        }
    }

    public LiveData<List<AdviceAndType>> getEatingAdvices(){
        return eatingAdvices;
    }

    public LiveData<List<AdviceAndType>> getGymnasticAdvices() {
        return gymnasticAdvices;
    }
}
