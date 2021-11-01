package com.example.homeweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyForecastViewHolder extends RecyclerView.ViewHolder {
    TextView dailyForecastTime;
    TextView dailyForecastTempRange;
    TextView dailyForecastWeatherDesc;
    TextView dailyForecastPrecipitation;
    TextView dailyForecastUvi;
    ImageView dailyForecastWeatherIcon;
    TextView dailyForecastMorningTemperature;
    TextView dailyForecastDayTemperature;
    TextView dailyForecastEveningTemperature;
    TextView dailyForecastNightTemperature;
    TextView dailyForecastMorningTime;
    TextView dailyForecastDayTime;
    TextView dailyForecastEveningTime;
    TextView dailyForecastNightTime;

    public DailyForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        this.dailyForecastTime = itemView.findViewById(R.id.dailyForecastTime);
        this.dailyForecastTempRange = itemView.findViewById(R.id.dailyForecastTempRange);

        this.dailyForecastWeatherDesc = itemView.findViewById(R.id.dailyForecastWeatherDesc);
        this.dailyForecastPrecipitation = itemView.findViewById(R.id.dailyForecastPrecipitation);
        this.dailyForecastUvi = itemView.findViewById(R.id.dailyForecastUvi);
        this.dailyForecastWeatherIcon = itemView.findViewById(R.id.dailyForecastWeatherIcon);
        this.dailyForecastMorningTemperature = itemView.findViewById(R.id.dailyForecastMorningTemperature);
        this.dailyForecastDayTemperature = itemView.findViewById(R.id.dailyForecastDayTemperature);
        this.dailyForecastEveningTemperature = itemView.findViewById(R.id.dailyForecastEveningTemperature);
        this.dailyForecastNightTemperature = itemView.findViewById(R.id.dailyForecastNightTemperature);
        this.dailyForecastMorningTime = itemView.findViewById(R.id.dailyForecastMorningTime);
        this.dailyForecastDayTime = itemView.findViewById(R.id.dailyForecastDayTime);
        this.dailyForecastEveningTime = itemView.findViewById(R.id.dailyForecastEveningTime);
        this.dailyForecastNightTime = itemView.findViewById(R.id.dailyForecastNightTime);
    }
}
