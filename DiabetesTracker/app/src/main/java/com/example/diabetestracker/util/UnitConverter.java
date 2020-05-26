package com.example.diabetestracker.util;

public class UnitConverter {
    public static int mmol_To_mg(float mmol) {
        return Math.round(mmol * 18);
    }

    public static float mg_To_mmol(int mg) {
        return Math.round(mg / 18f * 10f) / 10f;
    }
}
