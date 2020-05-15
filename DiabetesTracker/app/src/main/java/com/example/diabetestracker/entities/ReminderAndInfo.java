package com.example.diabetestracker.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ReminderAndInfo {
    @Embedded
    private Reminder reminder;

    @Relation(
            parentColumn = "id",
            entityColumn = "reminder_id"
    )
    private List<ReminderInfo> infos;

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public List<ReminderInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<ReminderInfo> infos) {
        this.infos = infos;
    }
}
