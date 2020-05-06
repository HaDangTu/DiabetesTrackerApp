package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "scales")
public class Scale {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "max")
    private float max;

    @ColumnInfo(name = "min")
    private float min;

    public Scale(){

    }

    @Ignore
    public Scale(int id, String name, float min, float max) {
        this.id = id;
        this.name = name;
        this.max = max;
        this.min = min;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + name + "min: " + min + "max: " + max;
    }
}
