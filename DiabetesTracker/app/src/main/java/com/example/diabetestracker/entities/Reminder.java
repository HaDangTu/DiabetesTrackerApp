package com.example.diabetestracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "reminder_time")
    private String time;

    @ColumnInfo(name = "enabled")
    private boolean enabled;

    @ColumnInfo(name = "monday")
    private boolean monday;

    @ColumnInfo(name = "tuesday")
    private boolean tuesday;

    @ColumnInfo(name = "wednesday")
    private boolean wednesday;

    @ColumnInfo(name = "thursday")
    private boolean thursday;

    @ColumnInfo(name = "friday")
    private boolean friday;

    @ColumnInfo(name = "saturday")
    private boolean saturday;

    @ColumnInfo(name = "sunday")
    private boolean sunday;

    @ColumnInfo(name = "type_id")
    private int typeId;

    public Reminder() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @NonNull
    @Override
    public String toString() {
        return time;
    }
}
