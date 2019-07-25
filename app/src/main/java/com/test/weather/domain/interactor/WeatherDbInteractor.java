package com.test.weather.domain.interactor;

import com.google.gson.Gson;

import com.test.weather.domain.data.weahter.WeatherResponse;
import com.test.weather.domain.data.result.WeatherErrorResult;
import com.test.weather.domain.data.result.WeatherResult;
import com.test.weather.domain.data.result.WeatherSuccessrResult;
import com.test.weather.domain.repository.db.DatabaseRepository;

import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.EmptyStackException;

public class WeatherDbInteractor {

    private static final String LOG_TAG = "WeatherDbInteractor";

    private DatabaseRepository databaseRepository;

    public WeatherDbInteractor(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @WorkerThread
    public WeatherResult getWeather() {
        Log.wtf(LOG_TAG, "onHandleIntent: getting weather from ==== DATABASE ====");
        String weathertDbModel = databaseRepository.getLatestWeather();
        if (!weathertDbModel.isEmpty()) {
            Gson gson = new Gson();
            WeatherResponse weatherResponse = gson.fromJson(weathertDbModel, WeatherResponse.class);
            return new WeatherSuccessrResult(weatherResponse);
        } else {
            Log.d(LOG_TAG, "onHandleIntent: db is empty");
            return new WeatherErrorResult(new EmptyStackException());
        }
    }

}
