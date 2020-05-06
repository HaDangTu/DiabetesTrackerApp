package com.example.diabetestracker.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;

import java.util.List;

@Dao
public interface TagDao {

    @Insert
    void insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);

    @Query( value = "SELECT name FROM tags")
    LiveData<List<String>> findAll();

//    @Transaction
//    @Query( value = "SELECT * FROM scales WHERE id = :id")
//    SessionScale findSessionById(int id);
}
