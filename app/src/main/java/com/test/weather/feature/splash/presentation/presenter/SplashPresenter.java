package com.test.weather.feature.splash.presentation.presenter;

import com.test.weather.domain.repository.location.LocationPermissionRepository;
import com.test.weather.domain.repository.permission.LocationRepository;
import com.test.weather.feature.splash.presentation.SplashContract;

import android.support.annotation.NonNull;
import android.util.Log;

public class SplashPresenter implements SplashContract.Presenter {

    private static final String LOG_TAG = "SplashPresenter";

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private SplashContract.View view;

    private LocationRepository locationRepository;

    private LocationPermissionRepository locationPermissionRepository;

    public SplashPresenter(
            SplashContract.View view,
            LocationRepository locationRepository,
            LocationPermissionRepository locationPermissionRepository
    ) {
        this.view = view;
        this.locationRepository = locationRepository;
        this.locationPermissionRepository = locationPermissionRepository;
    }

    @Override
    public void checkLocationPermission() {
        if (!locationPermissionRepository.isLocationPermissionGranted()) {
            Log.d(LOG_TAG, "requestPermissions: ");
            view.requestPermissions(REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            Log.d(LOG_TAG, "requestPermissions: granted");
            checkGpsEnable();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {

        if (requestCode != REQUEST_CODE_ASK_PERMISSIONS) return;

        if (!locationPermissionRepository.onRequestPermissionsResult(grantResults)) {
            view.openWeatherScreen();
            return;
        }

        checkGpsEnable();

    }

    @Override
    public void onGpsDialogDismiss() {
        view.openWeatherScreen();
    }

    @Override
    public void onNextButtonClicked() {
        view.openWeatherScreen();
    }

    private void checkGpsEnable() {
        if (!locationRepository.isGpsEnable()) {
            Log.d(LOG_TAG, "isGpsEnable: false");
            view.openGpsDialog();
        } else {
            Log.d(LOG_TAG, "isGpsEnable: true");
            view.openWeatherScreen();
        }
    }


}
