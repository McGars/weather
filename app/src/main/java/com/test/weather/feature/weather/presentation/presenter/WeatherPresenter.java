package com.test.weather.feature.weather.presentation.presenter;

import com.test.weather.api.WeatherApi;
import com.test.weather.domain.data.weahter.WeatherResponse;
import com.test.weather.domain.interactor.WeatherInteractor;
import com.test.weather.feature.weather.presentation.WeatherContract;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;

public class WeatherPresenter implements WeatherContract.Presenter, LifecycleObserver {

    private static final String LOG_TAG = "WeatherPresenter";

    private final WeatherContract.View view;

    private WeatherInteractor repository;


    public WeatherPresenter(
            LifecycleOwner lifecycleOwner,
            @NonNull WeatherContract.View view,
            WeatherInteractor repository
    ) {
        this.view = view;
        this.repository = repository;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void getWeather() {
        Log.wtf(LOG_TAG, "getWeather: getting weather");

        view.showLoading();

        repository.getWeather(new WeatherApi.RepositoryServiceCallback<WeatherResponse>() {
            @Override
            public void onResult(WeatherResponse result) {
                Log.wtf(LOG_TAG, "onResult: callback");
                onResultSuccess(result);
            }

            @Override
            public void onError(String error) {
                Log.wtf(LOG_TAG, "onError: callback");
                onServiceError(error);
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.d(LOG_TAG, "stop: ");
        repository.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.d(LOG_TAG, "start: ");
        getWeather();
    }

    private void onResultSuccess(WeatherResponse response) {
        Log.wtf(LOG_TAG, "onResultSuccess: ");
        view.hideLoading();
        view.showWeather(response);
    }

    private void onServiceError(String errorMessage) {
        Log.wtf(LOG_TAG, "onServiceError: ");
        view.hideLoading();
        view.showError(errorMessage);
    }
}
