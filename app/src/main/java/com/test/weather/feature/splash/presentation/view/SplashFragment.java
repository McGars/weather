package com.test.weather.feature.splash.presentation.view;

import com.test.weather.App;
import com.test.weather.R;
import com.test.weather.core.navigation.NavigationUtil;
import com.test.weather.feature.splash.presentation.SplashContract;
import com.test.weather.domain.repository.location.LocationPermissionRepository;
import com.test.weather.domain.repository.permission.LocationRepository;
import com.test.weather.databinding.FragmentSplashBinding;
import com.test.weather.feature.splash.presentation.presenter.SplashPresenter;
import com.test.weather.feature.weather.presentation.view.WeatherFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SplashFragment extends Fragment implements SplashContract.View {

    private static final String LOG_TAG = "SplashFragment";

    private static final int SPLASH_DELAY = 1000;

    private static final String REQUIRED_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private FragmentSplashBinding viewBinding;

    private SplashContract.Presenter presenter;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter(
                this,
                new LocationRepository(App.getInstance()),
                new LocationPermissionRepository(App.getInstance())
        );
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {

        viewBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_splash, container, false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding.bNext.setOnClickListener(v -> presenter.onNextButtonClicked());
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.checkLocationPermission();
    }

    @Override
    public void requestPermissions(int requestCode) {
        Log.d(LOG_TAG, "requestPermissions");
        requestPermissions(new String[]{REQUIRED_LOCATION_PERMISSION}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void openGpsDialog() {
        Log.d(LOG_TAG, "openGpsDialog");
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.gps_request)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    Log.d(LOG_TAG, "openLocationSettings");
                    NavigationUtil.openLocationSettings(getActivity());
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    Log.d(LOG_TAG, "onGpsDialogDismiss");
                })
                .setOnDismissListener( dialog -> presenter.onGpsDialogDismiss())
                .create()
                .show();

        viewBinding.bNext.setVisibility(View.VISIBLE);

    }

    @Override
    public void openWeatherScreen() {
        Log.d(LOG_TAG, "openWeatherScreen");
        final Handler handler = new Handler();
        handler.postDelayed(this::proceedToWeather, SPLASH_DELAY);
    }

    private void proceedToWeather() {
        NavigationUtil.openScreen(getActivity(), WeatherFragment.newInstance(), false);
    }

}
