package com.example.homeweather;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Current implements Serializable {
    private Date date;
    private Date sunrise;
    private Date sunset;
    private Integer temp;
    private Integer feelsLike;
    private Integer pressure;
    private Integer humidity;
    private Integer dewPoint;
    private Integer uvi;
    private Integer clouds;
    private Long visibility;
    private Double windSpeed;
    private Double windDeg;
    private Double windGust;
    private Double rain;
    private Double snow;
    private List<WeatherDetails> weatherDetailsList;

    public Current(Date date, Date sunrise, Date sunset, Integer temp, Integer feelsLike,
                   Integer pressure, Integer humidity, Integer dewPoint, Integer uvi,
                   Integer clouds, Long visibility, Double windSpeed, Double windDeg,
                   Double windGust, Double rain, Double snow,
                   List<WeatherDetails> weatherDetailsList) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.rain = rain;
        this.snow = snow;
        this.weatherDetailsList = weatherDetailsList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Integer feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getUvi() {
        return uvi;
    }

    public void setUvi(Integer uvi) {
        this.uvi = uvi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Double windDeg) {
        this.windDeg = windDeg;
    }

    public Double getWindGust() {
        return windGust;
    }

    public void setWindGust(Double windGust) {
        this.windGust = windGust;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }

    public void setWeatherDetailsList(List<WeatherDetails> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
    }
}
