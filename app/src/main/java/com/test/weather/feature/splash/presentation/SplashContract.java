package com.test.weather.feature.splash.presentation;

import android.support.annotation.NonNull;

public class SplashContract {

    public interface View {

        void requestPermissions(int requestCode);

        void openWeatherScreen();

        void openGpsDialog();
    }

    public interface Presenter {

        void checkLocationPermission();

        void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults);

        void onGpsDialogDismiss();

        void onNextButtonClicked();
    }

}
