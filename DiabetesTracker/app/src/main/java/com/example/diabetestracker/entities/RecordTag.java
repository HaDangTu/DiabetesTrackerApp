package com.example.diabetestracker.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class RecordTag {
    @Embedded private BloodSugarRecord record;

    @Relation(
            entity = Tag.class,
            parentColumn = "tag_id",
            entityColumn = "id"
    )
    private TagScale tagScale;

    public TagScale getTagScale() {
        return tagScale;
    }

    public void setTagScale(TagScale tagScale) {
        this.tagScale = tagScale;
    }

    public BloodSugarRecord getRecord() {
        return record;
    }

    public void setRecord(BloodSugarRecord record) {
        this.record = record;
    }
}
