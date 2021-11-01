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

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastViewHolder> {

    private final List<DailyForecast> dailyForecastList;
    private DailyForecastActivity dailyForecastActivity;
    private Unit unit;
    private Weather weather;

    public DailyForecastAdapter(List<DailyForecast> dailyForecastList,
                                DailyForecastActivity dailyForecastActivity, Unit unit, Weather weather) {
        this.dailyForecastList = dailyForecastList;
        this.dailyForecastActivity = dailyForecastActivity;
        this.unit = unit;
        this.weather = weather;
    }


    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View inflatedLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_forecast_weather_list, parent, false);
        return new DailyForecastViewHolder(inflatedLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position) {
        DailyForecast dailyForecast = this.dailyForecastList.get(position);

        LocalDateTime localDateTime =
                LocalDateTime.ofEpochSecond(dailyForecast.getDt().getTime() +
                        weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("EEEE, MM/dd", Locale.getDefault());

        holder.dailyForecastTime.setText(dateTimeFormatter.format(localDateTime));
        String dailyForecastTempRange = String.format("%s / %s", dailyForecast.getTemperature().
                        getMaximum() + Unit.formatUnit(unit),
                dailyForecast.getTemperature().getMinimum() + Unit.formatUnit(unit));
        holder.dailyForecastTempRange.setText(dailyForecastTempRange);

        holder.dailyForecastWeatherDesc.setText(Utilities.capitalizeFirstCharacter(dailyForecast.getWeatherDetailsList().get(0).
                getDescription()));

        String dailyForecastPrecipitation = String.format("(%s%% precip.)",
                dailyForecast.getPop());
        holder.dailyForecastPrecipitation.setText(dailyForecastPrecipitation);

        String dailyForecastUvi = String.format("UV Index: %s", dailyForecast.getUvi());
        holder.dailyForecastUvi.setText(dailyForecastUvi);

        String iconCode = "_" + dailyForecast.getWeatherDetailsList().get(0).getIcon();
        holder.dailyForecastWeatherIcon.setImageResource(dailyForecastActivity.getResources().
                getIdentifier(iconCode, "drawable", dailyForecastActivity.getPackageName()));

        String dailyForecastMorningTemperature = String.format("%s%s",
                dailyForecast.getTemperature().getMorning(), Unit.formatUnit(unit));
        holder.dailyForecastMorningTemperature.setText(dailyForecastMorningTemperature);

        String dailyForecastDayTemperature = String.format("%s%s",
                dailyForecast.getTemperature().getDay(), Unit.formatUnit(unit));
        holder.dailyForecastDayTemperature.setText(dailyForecastDayTemperature);

        String dailyForecastEveningTemperature = String.format("%s%s",
                dailyForecast.getTemperature().getEvening(), Unit.formatUnit(unit));
        holder.dailyForecastEveningTemperature.setText(dailyForecastEveningTemperature);

        String dailyForecastNightTemperature = String.format("%s%s",
                dailyForecast.getTemperature().getNight(), Unit.formatUnit(unit));
        holder.dailyForecastNightTemperature.setText(dailyForecastNightTemperature);


        holder.dailyForecastMorningTime.setText(Time.MORNING.value);
        holder.dailyForecastDayTime.setText(Time.DAY.value);
        holder.dailyForecastEveningTime.setText(Time.EVENING.value);
        holder.dailyForecastNightTime.setText(Time.NIGHT.value);
    }

    @Override
    public int getItemCount() {
        return dailyForecastList.size();
    }
}
