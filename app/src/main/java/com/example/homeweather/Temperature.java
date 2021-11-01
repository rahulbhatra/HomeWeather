package com.example.homeweather;

import java.io.Serializable;

public class Temperature implements Serializable {
    private Integer day;
    private Integer minimum;
    private Integer maximum;
    private Integer night;
    private Integer evening;
    private Integer morning;

    public Temperature(Integer day, Integer minimum, Integer maximum, Integer night, Integer evening, Integer morning) {
        this.day = day;
        this.minimum = minimum;
        this.maximum = maximum;
        this.night = night;
        this.evening = evening;
        this.morning = morning;
    }

    /* Use for Feels Like */
    public Temperature(Integer day, Integer night, Integer evening, Integer morning) {
        this.day = day;
        this.night = night;
        this.evening = evening;
        this.morning = morning;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getNight() {
        return night;
    }

    public void setNight(Integer night) {
        this.night = night;
    }

    public Integer getEvening() {
        return evening;
    }

    public void setEvening(Integer evening) {
        this.evening = evening;
    }

    public Integer getMorning() {
        return morning;
    }

    public void setMorning(Integer morning) {
        this.morning = morning;
    }
}
