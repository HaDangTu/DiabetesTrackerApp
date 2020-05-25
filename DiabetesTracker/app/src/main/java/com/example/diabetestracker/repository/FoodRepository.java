package com.example.diabetestracker.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.ApplicationDatabase;
import com.example.diabetestracker.daos.FoodDao;
import com.example.diabetestracker.entities.Food;
import com.example.diabetestracker.entities.FoodAndType;

import java.util.List;

public class FoodRepository extends BaseRepository {
    private FoodDao foodDao;

    public FoodRepository(Context context) {
        super(context);
        foodDao = database.foodDao();
    }

    public void insert (final Food food) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodDao.insert(food);
            }
        });
    }

    public void insert (final List<Food> foods) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodDao.insert(foods);
            }
        });
    }

    public void update (final Food food) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodDao.update(food);
            }
        });
    }

    public void delete (final Food food) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodDao.delete(food);
            }
        });
    }

    public void delete (final List<Food> foods) {
        ApplicationDatabase.dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodDao.delete(foods);
            }
        });
    }

    public LiveData<List<Food>> getAll(){
        return foodDao.findAll();
    }

    public LiveData<FoodAndType> getByName(String name) {
        return foodDao.findByName(name);
    }
}
