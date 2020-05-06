package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "blood_sugar_records")
public class BloodSugarRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name ="blood_sugar_level")
    private float bloodSugarLevel;

    @ColumnInfo(name = "record_date")
    private String recordDate;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "tag_id")
    private int tagId;

    public BloodSugarRecord(){

    }

    @Ignore
    public BloodSugarRecord(int id, float level, String recordDate, String note, int tagId){
        this.id = id;
        this.bloodSugarLevel = level;
        this.recordDate = recordDate;
        this.note = note;
        this.tagId = tagId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(float bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Sugar level: " + bloodSugarLevel + "\nDate: " + recordDate.toString();
    }
}
