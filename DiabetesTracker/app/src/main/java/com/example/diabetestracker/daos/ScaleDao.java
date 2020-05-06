package com.example.diabetestracker.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.diabetestracker.entities.Scale;

import java.util.List;

@Dao
public interface ScaleDao {

    @Insert
    void insert(Scale scale);

    @Query(value = "SELECT * FROM scales")
    List<Scale> findAll();
}
