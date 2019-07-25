package com.test.weather.domain.interactor;

import com.google.gson.Gson;

import com.test.weather.App;
import com.test.weather.R;
import com.test.weather.core.exception.ApiException;
import com.test.weather.domain.data.weahter.WeatherResponse;
import com.test.weather.core.network.NetworkResponseError;
import com.test.weather.core.network.NetworkResponseSuccess;
import com.test.weather.core.network.NetworkResult;
import com.test.weather.core.network.NetworkUtil;
import com.test.weather.domain.data.result.WeatherErrorResult;
import com.test.weather.domain.data.result.WeatherResult;
import com.test.weather.domain.data.result.WeatherSuccessrResult;
import com.test.weather.domain.repository.db.DatabaseRepository;

import android.support.annotation.StringRes;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherNetworkInteractor {

    private static final String LOG_TAG = "WeatherNetworkInteractor";

    private DatabaseRepository databaseRepository;

    public WeatherNetworkInteractor(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @WorkerThread
    public WeatherResult getWeatherByLatLon(double lat, double lon) {
        Log.wtf(LOG_TAG, "getWeatherByLatLon: ");

        try {

            if (!NetworkUtil.isConnectedToInternet()) {
                return getErrorResult(R.string.error_network_connection, null);
            }

            URL latLonRequestUrl = NetworkUtil.getLatLonRequestUrl(String.valueOf(lat), String.valueOf(lon));
            NetworkResult status = NetworkUtil.get(latLonRequestUrl);

            if (status instanceof NetworkResponseError) {
                return getErrorResult(R.string.error_server, ((NetworkResponseError) status).getError());
            } else if (status instanceof NetworkResponseSuccess) {
                Gson gson = new Gson();
                String response = ((NetworkResponseSuccess) status).getResponse();
                WeatherResponse weatherResponse = gson.fromJson(response, WeatherResponse.class);
                databaseRepository.weather(response);
                return new WeatherSuccessrResult(weatherResponse);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            getErrorResult(R.string.error_server, e);
        }

        return getErrorResult(R.string.error_server, null);
    }

    private WeatherErrorResult getErrorResult(@StringRes int message, Exception e) {
        return new WeatherErrorResult(new ApiException(App.getInstance().getString(message), e));
    }



}
