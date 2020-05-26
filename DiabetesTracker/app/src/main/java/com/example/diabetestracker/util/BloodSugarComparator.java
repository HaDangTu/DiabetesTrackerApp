package com.example.diabetestracker.util;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;

import java.util.Comparator;

public class BloodSugarComparator implements Comparator<RecordTag> {
    /**
     * using for sort descending
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(RecordTag o1, RecordTag o2) {
        BloodSugarRecord record1 = o1.getRecord();
        BloodSugarRecord record2 = o2.getRecord();

        float bloodSugar1 = record1.getGlycemicIndexMMol();
        float bloodSugar2 = record2.getGlycemicIndexMMol();

        if (bloodSugar1 < bloodSugar2)
            return 1;
        else if (bloodSugar1 > bloodSugar2)
            return -1;
        return 0;
    }

    @Override
    public Comparator<RecordTag> reversed() {
        return new Comparator<RecordTag> () {
            @Override
            public int compare(RecordTag o1, RecordTag o2) {
                BloodSugarRecord record1 = o1.getRecord();
                BloodSugarRecord record2 = o2.getRecord();

                float bloodSugar1 = record1.getGlycemicIndexMMol();
                float bloodSugar2 = record2.getGlycemicIndexMMol();

                if (bloodSugar1 > bloodSugar2)
                    return 1;
                else if (bloodSugar1 < bloodSugar2)
                    return -1;
                return 0;
            }
        };
    }
}
