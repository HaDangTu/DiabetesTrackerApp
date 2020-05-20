package com.example.diabetestracker.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.diabetestracker.daos.AdviceDao;
import com.example.diabetestracker.entities.AdviceAndType;

import java.util.List;

public class AdviceRepository extends BaseRepository {
    private AdviceDao adviceDao;
    public AdviceRepository(Context context) {
        super(context);

        adviceDao = database.adviceDao();
    }

    //find all eating advices
    public LiveData<List<AdviceAndType>> findAllEatingAdvices() {
        return adviceDao.findAllEatingAdvices();
    }

    //find all gymnastic advices
    public LiveData<List<AdviceAndType>> findAllGymnasticAdvices() {
        return adviceDao.findAllGymnasticAdvices();
    }

    //find all by type
    public LiveData<List<AdviceAndType>> findAllByType(String typeName) {
        return adviceDao.findAllByType(typeName);
    }

    public LiveData<List<AdviceAndType>> findAllByType(int typeId) {
        return adviceDao.findAllByType(typeId);
    }

    //get high glucose index warning
    public LiveData<String> getHighGlucoseWarning() {
        return adviceDao.getHighGlucoseWarning();
    }

    //get low glucose index warning
    public LiveData<String> getLowGlucoseWarning() {
        return adviceDao.getLowGlucoseWarning();
    }
}
