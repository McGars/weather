package com.test.weather.core.exception;

import android.support.annotation.Nullable;

import java.io.IOException;

public class ApiException extends IOException {

    public ApiException(final String message) {
        super(message);
    }

    public ApiException(final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }
}
