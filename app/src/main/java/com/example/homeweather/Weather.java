package com.example.homeweather;

import java.io.Serializable;
import java.util.List;

/**
 * @Rahul Sharma
 * To store all the data got from open weather api created json like structure of data inside
 * weather class.
 */
class Weather implements Serializable {
    private Double latitude;
    private Double longitude;
    private String timeZone;
    private Long timeZoneOffset;
    private Current current;
    private List<Hourly> hourlyList;
    private List<DailyForecast> dailyForecastList;

    public Weather(Double latitude, Double longitude, String timeZone, Long timeZoneOffset, Current current,
                   List<Hourly> hourlyList, List<DailyForecast> dailyForecastList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.timeZoneOffset = timeZoneOffset;
        this.current = current;
        this.hourlyList = hourlyList;
        this.dailyForecastList = dailyForecastList;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Long timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Hourly> getHourlyList() {
        return hourlyList;
    }

    public void setHourlyList(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }

    public List<DailyForecast> getDailyList() {
        return dailyForecastList;
    }

    public void setDailyList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }
}
