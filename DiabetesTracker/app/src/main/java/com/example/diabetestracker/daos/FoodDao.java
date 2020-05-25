package com.example.diabetestracker.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.diabetestracker.entities.Food;
import com.example.diabetestracker.entities.FoodAndType;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insert(Food food);

    @Insert
    void insert(List<Food> foods);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Delete
    void delete(List<Food> food);

    //find all food
    @Query(value = "SELECT * FROM foods")
    LiveData<List<Food>> findAll();

    //find food by name
    @Transaction
    @Query(value = "SELECT * FROM foods WHERE name = :name")
    LiveData<FoodAndType> findByName(String name);
}
