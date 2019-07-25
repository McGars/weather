package com.test.weather.api;

import com.test.weather.domain.data.weahter.WeatherResponse;

public interface WeatherApi {

    interface RepositoryServiceCallback<T> {
        void onResult(T result);
        void onError(String error);
    }

    void getWeather(RepositoryServiceCallback<WeatherResponse> callback);

}
