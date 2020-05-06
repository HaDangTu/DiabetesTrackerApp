package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "is_default")
    private boolean isDefault;

    @ColumnInfo(name = "scale_id")
    private int scaleId;


    public Tag(){

    }

    @Ignore
    public Tag(int id, String name, boolean isDefault, int scaleId) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.scaleId = scaleId;
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getScaleId() {
        return scaleId;
    }

    public void setScaleId(int scaleId) {
        this.scaleId = scaleId;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + isDefault;
    }
}
