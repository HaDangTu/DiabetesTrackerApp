package com.example.diabetestracker.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AdviceAndType {
    @Embedded private Advice advice;

    @Relation (
            parentColumn = "type_id",
            entityColumn = "id"
    )
    private AdviceType type;

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public AdviceType getType() {
        return type;
    }

    public void setType(AdviceType type) {
        this.type = type;
    }
}