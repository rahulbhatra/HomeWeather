package com.example.homeweather;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherViewHolder> {
    private final List<Hourly> hourlyList;
    private MainActivity mainActivity;
    private Unit unit;
    private Weather weather;

    public HourlyWeatherAdapter(List<Hourly> hourlyList, MainActivity mainActivity, Unit unit, Weather weather) {
        this.hourlyList = hourlyList;
        this.mainActivity = mainActivity;
        this.unit = unit;
        this.weather = weather;
    }

    @NonNull
    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_weather_list, parent, false);

        itemView.setOnClickListener(mainActivity);

        return new HourlyWeatherViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);

        String pattern = "HH:MM a";
        String timePattern = "EEEE";

        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(hourly.getDate().getTime() +
                        weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern(pattern, Locale.getDefault());

        DateTimeFormatter tf =
                DateTimeFormatter.ofPattern(timePattern, Locale.getDefault());

        holder.hourlyDay.setText(ldt.format(tf));
        holder.hourlyTime.setText(ldt.format(dtf));
        holder.hourlyTemperature.setText(String.format("%s%s", hourly.getTemp(), Unit.formatUnit(unit)));


        String iconCode = "_" + hourly.getWeatherDetailsList().get(0).getIcon();
        int iconResId = mainActivity.getResources().getIdentifier(iconCode,
                "drawable",
                mainActivity.getPackageName());
        holder.hourlyWeatherIcon.setImageResource(iconResId);

        holder.hourlyWeatherDescription.setText(Utilities.capitalizeFirstCharacter(hourly.
                getWeatherDetailsList().get(0).getDescription()));
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }
}