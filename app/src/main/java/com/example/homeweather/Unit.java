package com.example.homeweather;

import java.io.Serializable;

public enum Unit implements Serializable {
    IMPERIAL("imperial"), METRIC("metric");

    String unit;
    Unit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public static Unit getEnum(String unit) {
        if(unit.equalsIgnoreCase(IMPERIAL.toString())) {
            return IMPERIAL;
        } else {
            return METRIC;
        }
    }

    public static String formatUnit(Unit unit) {
        return unit.equals(METRIC) ? "°C" : "°F";
    }

    public static String formatUnit(String unit) {
        return unit.equalsIgnoreCase(METRIC.getUnit()) ? "°C" : "°F";
    }
}
