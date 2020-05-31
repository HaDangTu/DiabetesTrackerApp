package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

public class FoodAndType {
    @Embedded private Food food;

    @Relation(
            parentColumn = "type_id",
            entityColumn = "id"
    )
    private FoodType foodType;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    @NonNull
    @Override
    public String toString() {
        return food.getName();
    }
}
