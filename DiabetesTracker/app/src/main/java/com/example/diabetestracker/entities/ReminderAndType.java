package com.example.diabetestracker.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReminderAndType {
    @Embedded private Reminder reminder;

    @Relation(
            parentColumn = "type_id",
            entityColumn = "id"
    )
    private ReminderType type;

    public ReminderAndType() {

    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public ReminderType getType() {
        return type;
    }

    public void setType(ReminderType type) {
        this.type = type;
    }
}
