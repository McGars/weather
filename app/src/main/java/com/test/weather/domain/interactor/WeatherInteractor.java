package com.test.weather.domain.interactor;

import com.test.weather.api.WeatherApi;
import com.test.weather.core.location.UserLocationProvider;
import com.test.weather.domain.data.result.WeatherErrorResult;
import com.test.weather.domain.data.result.WeatherResult;
import com.test.weather.domain.data.result.WeatherSuccessrResult;
import com.test.weather.domain.data.weahter.WeatherResponse;

import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

public class WeatherInteractor implements WeatherApi, UserLocationProvider.LocationListener {

    private static final String LOG_TAG = "WeatherInteractor";

    private static final int MSG_LONG_RUNNING_OPERATION = 101;

    private static final int MSG_UPDATE_UI = 102;

    private final WeatherNetworkInteractor weatherNetworkInteractor;

    private final WeatherDbInteractor weatherDbInteractor;

    private UserLocationProvider locationProvider;

    private WeatherApi.RepositoryServiceCallback<WeatherResponse> callback;

    private HandlerThread handlerThread = new HandlerThread("bg-thread");

    Handler bgHandler;

    Handler uiHandler;

    public WeatherInteractor(
            WeatherNetworkInteractor weatherNetworkInteractor,
            WeatherDbInteractor weatherDbInteractor,
            UserLocationProvider locationProvider
    ) {
        this.weatherNetworkInteractor = weatherNetworkInteractor;
        this.weatherDbInteractor = weatherDbInteractor;
        this.locationProvider = locationProvider;
        locationProvider.setLocationListener(this);
        handlerThread.start();

        Handler.Callback handlerCallback = msg -> {
            switch (msg.what) {
                case MSG_LONG_RUNNING_OPERATION:
                    loadWeatherData();
                    break;
                case MSG_UPDATE_UI:
                    WeatherResult weatherResult = (WeatherResult) msg.obj;
                    doUpdateUi(weatherResult);
                    break;
            }
            return true;
        };

        bgHandler = new Handler(handlerThread.getLooper(), handlerCallback);

        uiHandler = new Handler(Looper.getMainLooper(), handlerCallback);
    }

    @Override
    public void getWeather(RepositoryServiceCallback<WeatherResponse> callback) {
        Log.wtf(LOG_TAG, "getWeather: ");

        this.callback = callback;

        bgHandler.sendEmptyMessage(MSG_LONG_RUNNING_OPERATION);

    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.wtf(LOG_TAG, "onLocationUpdated: LOCATION UPDATED --> Getting weather");

        bgHandler.sendEmptyMessage(MSG_LONG_RUNNING_OPERATION);
    }

    public void onStop() {
        Log.wtf(LOG_TAG, "onStop: ");
        locationProvider.onStop();

        bgHandler.removeCallbacksAndMessages(null);
        uiHandler.removeCallbacksAndMessages(null);

        // Shut down the background thread
        bgHandler.getLooper().quit();
    }

    @WorkerThread
    private void loadWeatherData() {
        // TODO switch to RxJava, Coroutines or Service
        WeatherResult weatherResult;

        Location location = locationProvider.getLocationFromService();
        if (!locationProvider.isServiceReady() || location == null) {
            weatherResult = weatherDbInteractor.getWeather();
        } else {
            weatherResult = weatherNetworkInteractor
                    .getWeatherByLatLon(location.getLatitude(), location.getLongitude());
        }

        uiHandler.obtainMessage(MSG_UPDATE_UI, weatherResult).sendToTarget();

    }

    @MainThread
    private void doUpdateUi(WeatherResult result) {
        if (result instanceof WeatherSuccessrResult) {
            callback.onResult(
                    ((WeatherSuccessrResult) result).getWeatherResponse());
        } else if (result instanceof WeatherErrorResult) {
            callback.onError(
                    ((WeatherErrorResult) result).getError().getMessage());
        }
    }

}
