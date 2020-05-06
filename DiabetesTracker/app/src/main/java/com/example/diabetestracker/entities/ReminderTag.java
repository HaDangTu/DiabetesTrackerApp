package com.example.diabetestracker.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReminderTag {
    @Embedded private Reminder reminder;

    @Relation(
            parentColumn = "tag_id",
            entityColumn = "id"
    )
    private Tag tag;

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
