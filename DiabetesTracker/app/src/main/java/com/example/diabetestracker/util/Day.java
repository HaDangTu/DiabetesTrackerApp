package com.example.diabetestracker.util;

public enum Day {
    MONDAY ("T2"),
    TUESDAY ("T3"),
    WEDNESDAY ("T4"),
    THURSDAY ("T5"),
    FRIDAY ("T6"),
    SATURDAY ("T7"),
    SUNDAY ("CN");

    public final String value;
    Day(String value) {
        this.value = value;
    }
}
