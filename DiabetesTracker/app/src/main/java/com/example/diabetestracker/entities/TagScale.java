package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

public class TagScale {
    @Embedded private Tag tag;

    @Relation(
            parentColumn = "scale_id",
            entityColumn = "id"
    )
    private Scale scale;

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public String toString() {
        return tag.getName();
    }
}
