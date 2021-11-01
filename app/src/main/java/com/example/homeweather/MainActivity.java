package com.example.homeweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentLocationName;
    private TextView currentDateTime;
    private TextView currentTemperature;
    private TextView currentFeelsLike;
    private TextView currentHumidity;
    private TextView currentUvi;
    private ImageView currentWeatherIcon;
    private TextView currentWeatherDescription;
    private TextView currentWinds;
    private TextView currentRainOrSnow;
    private TextView currentVisibility;
    private TextView morningTemperature;
    private TextView dayTimeTemperature;
    private TextView eveningTemperature;
    private TextView nightTemperature;
    private TextView morningTime;
    private TextView dayTime;
    private TextView eveningTime;
    private TextView nightTime;
    private TextView currentSunrise;
    private TextView currentSunset;
    private final List<Hourly> hourlyList = new ArrayList<>();

    private MenuItem homeMenuToggleUnits;

    private RecyclerView recyclerView;
    private HourlyWeatherAdapter hourlyWeatherAdapter;
    private Unit unit = Unit.METRIC;
    private Weather weather;
    private Double latitude = 41.8675766;
    private Double longitude = -87.616232;
    private String locationName = "";

    private SwipeRefreshLayout swiper; // The SwipeRefreshLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Open Weather App");
        recyclerView = findViewById(R.id.homeWeatherRecycler);
        getUserSetting();
        this.currentLocationName = findViewById(R.id.currentLocationName);
        this.currentDateTime = findViewById(R.id.currentDateTime);
        this.currentTemperature = findViewById(R.id.currentTemperature);
        this.currentFeelsLike = findViewById(R.id.currentFeelsLike);
        this.currentWeatherDescription = findViewById(R.id.currentWeatherDescription);
        this.currentWinds = findViewById(R.id.currentWinds);
        this.currentHumidity = findViewById(R.id.currentHumidity);
        this.currentUvi = findViewById(R.id.currentUvi);
        this.currentRainOrSnow = findViewById(R.id.currentRainOrSnow);
        this.currentVisibility = findViewById(R.id.currentVisibility);
        this.morningTemperature = findViewById(R.id.morningTemperature);
        this.dayTimeTemperature = findViewById(R.id.dayTimeTemperature);
        this.eveningTemperature = findViewById(R.id.eveningTemperature);
        this.nightTemperature = findViewById(R.id.nightTemperature);
        this.dayTime = findViewById(R.id.dayTimeTime);
        this.morningTime = findViewById(R.id.morningTime);
        this.eveningTime = findViewById(R.id.eveningTime);
        this.nightTime = findViewById(R.id.nightTime);
        this.currentSunrise = findViewById(R.id.currentSunrise);
        this.currentSunset = findViewById(R.id.currentSunset);
        this.currentWeatherIcon = findViewById(R.id.currentWeatherIcon);

        this.swiper = findViewById(R.id.swipeRefresh);
        this.swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherData();
                swiper.setRefreshing(false); // This stops the busy-circle
            }
        });

        getWeatherData();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Hourly hourly = this.hourlyList.get(pos);

        LocalDateTime localDateTime =
                LocalDateTime.ofEpochSecond(hourly.getDate().getTime() +
                        weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        Calendar cal = new GregorianCalendar();
        cal.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        Uri.Builder builder =
                CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");

        builder.appendPath(Long.toString(cal.getTime().getTime()));
        Intent intent =
                new Intent(Intent.ACTION_VIEW, builder.build());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_weather_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateWeatherData(Weather weatherData) {
        if (weatherData != null) {
            setWeatherData(weatherData);
            weather = weatherData;
            hourlyWeatherAdapter = new HourlyWeatherAdapter(weather.getHourlyList(), this, unit, weatherData);
            recyclerView.setAdapter(hourlyWeatherAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false));
            setHourlyRecyclerViewData(weatherData.getHourlyList());
        }
    }

    public void downloadFailed() {
        hourlyList.clear();
        hourlyWeatherAdapter.notifyItemRangeChanged(0, hourlyList.size());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeatherData(Weather weatherData) {

        Current current = weatherData.getCurrent();
        DailyForecast dailyForecast = weatherData.getDailyList().get(0);

        LocalDateTime localDateTime =
                LocalDateTime.ofEpochSecond(current.getDate().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        String iconCode = "_" + current.getWeatherDetailsList().get(0).getIcon();

        locationName = Utilities.getLocationName(this, weatherData.getLatitude(),
                weatherData.getLongitude());
        currentLocationName.setText(locationName);

        DateTimeFormatter dateTimeFormat =
                DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());

        currentDateTime.setText(localDateTime.format(dateTimeFormat));
        currentTemperature.setText(String.format("%s%s", current.getTemp(), Unit.formatUnit(unit)));
        currentFeelsLike.setText(String.format("Feels like %s%s", current.getFeelsLike(), Unit.formatUnit(unit)));
        currentWeatherDescription.setText(String.format("%s (%s Clouds)",
                Utilities.capitalizeFirstCharacter(current.getWeatherDetailsList().get(0).
                        getDescription()), current.getClouds() + "%"));

        String speedFormat = Utilities.speedFormat(this.unit);
        currentWinds.setText(String.format("Winds: %s at %s%s", Utilities.
                getDirection(current.getWindDeg()), current.getWindSpeed(),
                speedFormat) + (current.getWindGust() != null ?
                String.format(" gusting to %s%s", current.getWindGust(), speedFormat) : ""));
        currentHumidity.setText(String.format("Humidity: %s%%", current.getHumidity()));
        currentUvi.setText(String.format("UV Index: %s", current.getUvi()));

        String rainOrSnow = "";
        if(current.getRain() != null) {
            rainOrSnow = String.format("Last Hour Rain %s mm", current.getRain());
        }

        if(current.getSnow() != null) {
            rainOrSnow = String.format("Last Hour Snow %s mm", current.getSnow());
        }
        currentRainOrSnow.setText(rainOrSnow);
        currentVisibility.setText("Visibility: " +
                Utilities.rangeFormat(unit, current.getVisibility()));
        morningTemperature.setText(String.format("%s%s", dailyForecast.getTemperature().getMorning(), Unit.formatUnit(unit)));
        dayTimeTemperature.setText(String.format("%s%s", dailyForecast.getTemperature().getDay(), Unit.formatUnit(unit)));
        eveningTemperature.setText(String.format("%s%s", dailyForecast.getTemperature().getEvening(), Unit.formatUnit(unit)));
        nightTemperature.setText(String.format("%s%s", dailyForecast.getTemperature().getNight(), Unit.formatUnit(unit)));
        currentWeatherIcon.setImageResource(getResources().getIdentifier(iconCode, "drawable", getPackageName()));
        morningTime.setText(Time.MORNING.value);
        dayTime.setText(Time.DAY.value);
        eveningTime.setText(Time.EVENING.value);
        nightTime.setText(Time.NIGHT.value);

        DateTimeFormatter sunriseTimeFormat =
                DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());

        LocalDateTime sunriseDateTime =
                LocalDateTime.ofEpochSecond(current.getSunrise().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        LocalDateTime sunsetDateTime =
                LocalDateTime.ofEpochSecond(current.getSunset().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        currentSunrise.setText(String.format("Sunrise: %s", sunriseDateTime.format(sunriseTimeFormat)));
        currentSunset.setText(String.format("Sunset: %s", sunsetDateTime.format(sunriseTimeFormat)));
    }

    private void setHourlyRecyclerViewData(List<Hourly> hourlyList) {
        this.hourlyList.addAll(hourlyList);
        this.hourlyWeatherAdapter.notifyItemRangeChanged(0, hourlyList.size());
    }

    private void changeLocationAlertDialog() {
        if(Utilities.isNetworkConnectionAvailable(this)) {
            LayoutInflater inflater = LayoutInflater.from(this);
            final View view = inflater.inflate(R.layout.change_location_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Enter location");
            builder.setMessage("For US location, enter as 'City' or 'City, State' \n\n" +
                    "For international location, enter as 'City, Country' ");
            builder.setView(view);
            builder.setPositiveButton("OK", (dialog, id) -> {

                EditText alertDialogLocation = view.findViewById(R.id.alertDialogLocation);
                double[] latitudeLongitude = Utilities.getLatLon(alertDialogLocation.getText().toString(), this);
                if (latitudeLongitude != null) {
                    latitude = latitudeLongitude[0];
                    longitude = latitudeLongitude[1];
                    setUserSetting();
                    getWeatherData();
                } else {
                    Toast.makeText(this, "Invalid city/state", Toast.LENGTH_SHORT).show();
                }

            });
            builder.setNegativeButton("CANCEL", (dialog, id) -> {
                Toast.makeText(MainActivity.this, "Don't want to change the place?",
                        Toast.LENGTH_SHORT).show();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getWeatherData() {
        if (Utilities.isNetworkConnectionAvailable(this)) {
            WeatherRunnable weatherAPI = new WeatherRunnable(this, latitude, longitude, unit.getUnit());
            new Thread(weatherAPI).start();
        } else {
            currentLocationName.setText("");
            currentDateTime.setText(String.format("%s", "No Network Connection"));
            currentTemperature.setText("");
            currentFeelsLike.setText("");
            currentWeatherDescription.setText("");
            currentWinds.setText("");
            currentHumidity.setText("");
            currentUvi.setText("");
            currentRainOrSnow.setText("");
            currentVisibility.setText("");
            morningTemperature.setText("");
            dayTimeTemperature.setText("");
            eveningTemperature.setText("");
            nightTemperature.setText("");
            dayTime.setText("");
            morningTime.setText("");
            eveningTime.setText("");
            nightTime.setText("");
            currentSunrise.setText("");
            currentSunset.setText("");
            currentWeatherIcon.setImageResource(0);

            hourlyList.clear();
            hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyList, this, unit, weather);
            recyclerView.setAdapter(hourlyWeatherAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false));

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        homeMenuToggleUnits = menu.findItem(R.id.home_menu_toggle_units);
        Utilities.setUnits(homeMenuToggleUnits, unit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_menu_toggle_units:
                this.unit = unit.equals(Unit.METRIC) ? Unit.IMPERIAL : Unit.METRIC;
                setUserSetting();
                Utilities.setUnits(homeMenuToggleUnits, this.unit);
                getWeatherData();
                break;
            case R.id.home_menu_daily_forecast:
                openDailyForecastActivity();
                break;
            case R.id.home_menu_location:
                changeLocationAlertDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDailyForecastActivity() {

        if(Utilities.isNetworkConnectionAvailable(this)) {
            Intent intent = new Intent(this, DailyForecastActivity.class);
            intent.putExtra(String.valueOf(R.string.weather), weather);
            intent.putExtra(String.valueOf(R.string.unit), unit);
            intent.putExtra(String.valueOf(R.string.locale), locationName);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(String.valueOf(R.string.unit), unit.getUnit());
        sharedPreferencesEditor.putString(String.valueOf(R.string.latitude), latitude.toString());
        sharedPreferencesEditor.putString(String.valueOf(R.string.longitude), longitude.toString());
        sharedPreferencesEditor.apply();
    }

    public void getUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString(String.valueOf(R.string.unit), getString(R.string.metric));
        this.unit = Unit.getEnum(unit);
        String latitude = sharedPreferences.getString(String.valueOf(R.string.latitude), this.latitude.toString());
        this.latitude = Double.parseDouble(latitude);
        String longitude = sharedPreferences.getString(String.valueOf(R.string.longitude), this.longitude.toString());
        this.longitude = Double.parseDouble(longitude);
    }
}