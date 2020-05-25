package com.example.diabetestracker.util;

public class UnitConverter {
    public static float mmol_To_mg(float mmol) {
        return mmol * 18f;
    }

    public static float mg_To_mmol(float mg) {
        return mg / 18f;
    }
}
