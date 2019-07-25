package com.test.weather.domain.data.result;

import com.test.weather.domain.data.weahter.WeatherResponse;

public class WeatherSuccessrResult implements WeatherResult {

    private WeatherResponse weatherResponse;

    public WeatherSuccessrResult(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }
}
