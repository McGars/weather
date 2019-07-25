package com.test.weather.domain.data.result;

public class WeatherErrorResult implements WeatherResult {

    private Exception error;

    public WeatherErrorResult(Exception error) {
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
}
