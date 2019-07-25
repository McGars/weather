package com.test.weather.core.network;

public class NetworkResponseError implements NetworkResult {

    private Exception error;

    public NetworkResponseError(Exception error) {
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
}
