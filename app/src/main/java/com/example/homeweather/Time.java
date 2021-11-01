package com.example.homeweather;

public enum Time {
    MORNING("8am"), DAY("1pm"), EVENING("5pm"), NIGHT("11pm");

    String value;
    Time(String value) {
        this.value = value;
    }

    public String getTime() {
        return value;
    }
}
