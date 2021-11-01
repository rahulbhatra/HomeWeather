package com.example.homeweather;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastActivity extends AppCompatActivity {

    private List<DailyForecast> dailyForecastList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DailyForecastAdapter dailyForecastAdapter;
    private Weather weather;
    private Unit unit;
    private String locale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        recyclerView = findViewById(R.id.dailyForecastRecycler);


        Intent intent = getIntent();
        if(intent.hasExtra(String.valueOf(R.string.weather))
                && intent.hasExtra(String.valueOf(R.string.unit))
                && intent.hasExtra(String.valueOf(R.string.locale))) {
            this.weather = (Weather) intent.getSerializableExtra(String.valueOf(R.string.weather));
            this.unit = (Unit) intent.getSerializableExtra(String.valueOf(R.string.unit));
            this.locale = intent.getStringExtra(String.valueOf(R.string.locale));
            this.setTitle(locale);

            this.dailyForecastList.addAll(weather.getDailyList());
            this.dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList,
                    this, unit, weather);

            recyclerView.setAdapter(dailyForecastAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));
        }
    }
}
