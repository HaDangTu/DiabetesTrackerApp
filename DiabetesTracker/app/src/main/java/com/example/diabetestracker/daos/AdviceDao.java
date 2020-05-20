package com.example.diabetestracker.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.diabetestracker.entities.AdviceAndType;

import java.util.List;

@Dao
public interface AdviceDao {

    //Find all eating advice
    @Transaction
    @Query(value = "SELECT * FROM advices WHERE type_id = 1")
    LiveData<List<AdviceAndType>> findAllEatingAdvices();

    //Find all gymnastic advice
    @Transaction
    @Query(value = "SELECT * FROM advices WHERE type_id = 2")
    LiveData<List<AdviceAndType>> findAllGymnasticAdvices();

    //Find all by type name
    @Transaction
    @Query(value = "SELECT * FROM advices WHERE " +
            "type_id = (SELECT id FROM advice_types WHERE name = :name)")
    LiveData<List<AdviceAndType>> findAllByType(String name);

    //Find all by type id
    @Transaction
    @Query(value = "SELECT * FROM advices WHERE type_id = :type_id")
    LiveData<List<AdviceAndType>> findAllByType(int type_id);

    //Get high glucose index warning
    @Query(value = "SELECT description FROM advices WHERE title = 'Cao đường huyết'")
    LiveData<String> getHighGlucoseWarning();

    //Get low glucose index warnin
    @Query(value = "SELECT description FROM advices WHERE title = 'Hạ đường huyết'")
    LiveData<String> getLowGlucoseWarning();
}
