package com.test.weather.core.network;

public class NetworkResponseSuccess implements NetworkResult {

    private String response;

    public NetworkResponseSuccess(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
