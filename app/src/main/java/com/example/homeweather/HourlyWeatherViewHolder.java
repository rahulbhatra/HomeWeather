package com.example.homeweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {
    TextView hourlyDay;
    TextView hourlyTime;
    ImageView hourlyWeatherIcon;
    TextView hourlyTemperature;
    TextView hourlyWeatherDescription;

    public HourlyWeatherViewHolder(@NonNull View itemView, TextView hourlyDay, TextView hourlyTime, ImageView hourlyWeatherIcon, TextView hourlyTemperature, TextView hourlyWeatherDescription) {
        super(itemView);
        this.hourlyDay = hourlyDay;
        this.hourlyTime = hourlyTime;
        this.hourlyWeatherIcon = hourlyWeatherIcon;
        this.hourlyTemperature = hourlyTemperature;
        this.hourlyWeatherDescription = hourlyWeatherDescription;
    }

    public HourlyWeatherViewHolder(View view) {
        super(view);
        hourlyDay = view.findViewById(R.id.hourlyDay);
        hourlyTime = view.findViewById(R.id.hourlyTime);
        hourlyWeatherIcon = view.findViewById(R.id.hourlyWeatherIcon);
        hourlyTemperature = view.findViewById(R.id.hourlyTemprerature);
        hourlyWeatherDescription = view.findViewById(R.id.hourlyWeatherDescription);
    }
}

