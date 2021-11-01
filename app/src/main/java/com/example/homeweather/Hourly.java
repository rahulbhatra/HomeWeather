package com.example.homeweather;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Hourly implements Serializable {
    private Date date;
    private Integer temp;
    private List<WeatherDetails> weatherDetailsList;
    private Double pop;

    public Hourly(Date date, Integer temp, List<WeatherDetails> weatherDetailsList, Double pop) {
        this.date = date;
        this.temp = temp;
        this.weatherDetailsList = weatherDetailsList;
        this.pop = pop;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }

    public void setWeatherDetailsList(List<WeatherDetails> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }
}
