package com.example.diabetestracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diabetestracker.entities.Food;
import com.example.diabetestracker.entities.FoodAndType;
import com.example.diabetestracker.repository.FoodRepository;

import java.util.List;


public class FoodViewModel extends AndroidViewModel {
    private FoodRepository repository;
    private LiveData<List<Food>> foods;
    private LiveData<List<FoodAndType>> foodandtypes;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        if (foods == null)
            foods = repository.getAll();
        if (foodandtypes == null)
            foodandtypes = repository.findAllFoodAndType();
    }

    //get food by name
    public LiveData<FoodAndType> getFood(String name) {
        return repository.getByName(name);
    }
    public LiveData<List<FoodAndType>> getAllFood() {
        return foodandtypes;
    }
}
