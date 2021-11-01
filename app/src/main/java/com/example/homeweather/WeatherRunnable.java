package com.example.homeweather;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherRunnable implements Runnable {
    private static final String TAG = "WeatherDownloadRunnable";

    private final MainActivity mainActivity;
    private final Double latitude;
    private final Double longitude;
    private final String units;

    private static final String weatherURL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String iconUrl = "https://openweathermap.org/img/w/";

    //////////////////////////////////////////////////////////////////////////////////
    // Sign up to get your API Key at:  https://home.openweathermap.org/users/sign_up
    private static final String apiKey = "2f95965f5e5d5cdc5a34b095cc81eb61";
    //
    //////////////////////////////////////////////////////////////////////////////////


    public WeatherRunnable(MainActivity mainActivity, Double latitude, Double longitude, String units) {
        this.mainActivity = mainActivity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.units = units;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();
        buildURL.appendQueryParameter("units", units);
        buildURL.appendQueryParameter("lat", String.format("%s", latitude));
        buildURL.appendQueryParameter("lon", String.format("%s", longitude));
        buildURL.appendQueryParameter("appid", apiKey);

        String urlToUse = buildURL.build().toString();

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK: " + conn.getResponseCode() + " , " +conn.getResponseMessage());
                handleResults(null);
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            reader.close();
            is.close();

            Log.d(TAG, "run: " + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        handleResults(sb.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleResults(String s) {

        if (s == null) {
            Log.d(TAG, "handleResults: Failure in data download");
            mainActivity.runOnUiThread(mainActivity::downloadFailed);
            return;
        }

        final Weather weatherData = parseJSON(s);
        mainActivity.runOnUiThread(() -> {
            if (weatherData != null) {
                mainActivity.updateWeatherData(weatherData);
            }
        });
    }

    private Weather parseJSON(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);

            double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");

            String timezone = jsonObject.getString("timezone");
            long timezoneOffset = jsonObject.getLong("timezone_offset");
            Current current = null;
            List<Hourly> hourly = new ArrayList<>();
            List<DailyForecast> dailyForecast = new ArrayList<>();

            if (jsonObject.has("current")) {
                JSONObject currentObj = jsonObject.getJSONObject("current");
                JSONArray currentWeatherJsonArray = currentObj.getJSONArray("weather");

                List<WeatherDetails> currentWeatherInfo = new ArrayList<>();
                if (currentWeatherJsonArray.length() > 0) {
                    JSONObject currentWeather = currentWeatherJsonArray.getJSONObject(0);
                    WeatherDetails weatherInfo = new WeatherDetails(
                            currentWeather.getLong("id"),
                            currentWeather.getString("main"),
                            currentWeather.getString("description"),
                            currentWeather.getString("icon")
                    );
                    currentWeatherInfo.add(weatherInfo);
                }

                Double windGust = null;
                if(currentObj.has("wind_gust")) {
                    windGust = currentObj.getDouble("wind_gust");
                }

                Double rain = null;
                if(currentObj.has("rain")) {
                    JSONObject rainObject = currentObj.getJSONObject("rain");
                    rain = rainObject.getDouble("1h");
                }

                Double snow = null;
                if(currentObj.has("snow")) {
                    JSONObject snowObject = currentObj.getJSONObject("snow");
                    snow = snowObject.getDouble("1h");
                }

                current = new Current(
                        new Date(currentObj.getLong("dt")),
                        new Date(currentObj.getLong("sunrise")),
                        new Date(currentObj.getLong("sunset")),
                        currentObj.getInt("temp"),
                        currentObj.getInt("feels_like"),
                        currentObj.getInt("pressure"),
                        currentObj.getInt("humidity"),
                        currentObj.getInt("dew_point"),
                        currentObj.getInt("uvi"),
                        currentObj.getInt("clouds"),
                        currentObj.getLong("visibility"),
                        currentObj.getDouble("wind_speed"),
                        currentObj.getDouble("wind_deg"),
                        windGust,
                        rain,
                        snow,
                        currentWeatherInfo
                );
            }

            if (jsonObject.has("hourly")) {
                JSONArray hourlyJsonArray = jsonObject.getJSONArray("hourly");

                for (int i = 0; i < hourlyJsonArray.length(); i++) {
                    JSONObject jo = hourlyJsonArray.getJSONObject(i);
                    JSONArray hourlyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> hourlyWeatherInfo = new ArrayList<>();
                    if (hourlyWeatherJsonArray.length() > 0) {
                        JSONObject hourlyWeather = hourlyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                hourlyWeather.getLong("id"),
                                hourlyWeather.getString("main"),
                                hourlyWeather.getString("description"),
                                hourlyWeather.getString("icon")
                        );
                        hourlyWeatherInfo.add(weatherInfo);
                    }

                    Hourly h = new Hourly(
                            new Date(jo.getLong("dt")),
                            jo.getInt("temp"),
                            hourlyWeatherInfo,
                            jo.getDouble("pop")
                    );

                    hourly.add(i, h);
                }
            }

            if (jsonObject.has("daily")) {
                JSONArray dailyJsonArray = jsonObject.getJSONArray("daily");

                for (int i = 0; i < dailyJsonArray.length(); i++) {
                    JSONObject jo = dailyJsonArray.getJSONObject(i);
                    JSONArray dailyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> dailyWeatherInfo = new ArrayList<>();
                    if (dailyWeatherJsonArray.length() > 0) {
                        JSONObject dailyWeather = dailyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                dailyWeather.getLong("id"),
                                dailyWeather.getString("main"),
                                dailyWeather.getString("description"),
                                dailyWeather.getString("icon")
                        );
                        dailyWeatherInfo.add(weatherInfo);
                    }


                    Temperature temperature = null;
                    if (jo.has("temp")) {
                        JSONObject j = jo.getJSONObject("temp");
                        temperature = new Temperature(
                                j.getInt("day"),
                                j.getInt("min"),
                                j.getInt("max"),
                                j.getInt("night"),
                                j.getInt("eve"),
                                j.getInt("morn")
                        );
                    }

                    DailyForecast d = new DailyForecast(
                            new Date(jo.getLong("dt")),
                            jo.getInt("pop"),
                            jo.getInt("uvi"),
                            temperature,
                            dailyWeatherInfo
                    );

                    dailyForecast.add(i, d);
                }
            }

            return new Weather(lat, lon, timezone, timezoneOffset, current, hourly, dailyForecast);
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
