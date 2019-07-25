package com.test.weather.util;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import static com.test.weather.util.WeatherIconHelper.getIconForWeatherCondition;

public class WeatherAdapter {
    private static final String LOG_TAG = "WeatherAdapter";

    @BindingAdapter("weatherIcon")
    public static void getWeatherIcon(ImageView imageView, int weatherDesc) {
        Log.wtf(LOG_TAG, "getWeatherIcon: " + weatherDesc);
        if (weatherDesc != 0) {
            imageView.setImageResource(getIconForWeatherCondition(weatherDesc));
        }
    }
}
