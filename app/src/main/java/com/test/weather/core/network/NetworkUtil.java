package com.test.weather.core.network;

import com.test.weather.App;
import com.test.weather.BuildConfig;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtil {
    private static final String LOG_TAG = "NetworkUtil";

    public static final String API_KEY = "ed3724d4850e07c530135c5488eba79c";

    public static final String WEATHER_REQUEST = "https://api.openweathermap.org/data/2.5/weather?lat=%1$s&lon=%2$s&units=metric&lang=ru&APPID=" + API_KEY;

    private static final String REQUEST_METHOD_GET = "GET";

    public static URL getLatLonRequestUrl(@NonNull String lat, @NonNull String lon) throws MalformedURLException {
        return new URL(String.format(WEATHER_REQUEST, lat, lon));
    }

    public static boolean isConnectedToInternet() {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            isConnected = ni != null && ni.isAvailable() && ni.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "isConnectedToInternet: " + isConnected);
        return isConnected;
    }

    @Nullable
    public static NetworkResult get(URL url) {

        String response;
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder readStringBuilder = new StringBuilder();

        try {
            // Open http connection
            connection = (HttpURLConnection) url.openConnection();
            // Set request method
            connection.setRequestMethod(REQUEST_METHOD_GET);
            // Connection timeout & read timeout
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {

                // Input stream from url connection
                InputStream inputStream = connection.getInputStream();

                inputStreamReader = new InputStreamReader(inputStream);

                bufferedReader = new BufferedReader(inputStreamReader);

                // Reading server response
                String line = bufferedReader.readLine();

                while (line != null) {
                    // Gather all lines in string buffer
                    readStringBuilder.append(line);
                    // Read line
                    line = bufferedReader.readLine();
                }
                response = readStringBuilder.toString();

                if (BuildConfig.DEBUG) {
                    Log.d(LOG_TAG, response);
                }

                return new NetworkResponseSuccess(response);
            } else {
                return new NetworkResponseError(new NetworkErrorException("Something went wrong, response code " + statusCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new NetworkResponseError(new NetworkErrorException(e.getMessage()));
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
