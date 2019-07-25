package com.test.weather.feature.weather.presentation.view;

import com.test.weather.App;
import com.test.weather.R;
import com.test.weather.domain.data.weahter.WeatherResponse;
import com.test.weather.feature.weather.model.WeatherViewModel;
import com.test.weather.core.location.UserLocationProvider;
import com.test.weather.domain.interactor.WeatherDbInteractor;
import com.test.weather.domain.interactor.WeatherNetworkInteractor;
import com.test.weather.domain.repository.db.DatabaseRepository;
import com.test.weather.domain.interactor.WeatherInteractor;
import com.test.weather.databinding.FragmentWeatherBinding;
import com.test.weather.feature.weather.presentation.WeatherContract;
import com.test.weather.feature.weather.presentation.presenter.WeatherPresenter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class WeatherFragment extends Fragment implements WeatherContract.View {

    private static final String LOG_TAG = "WeatherFragment";

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    private WeatherPresenter presenter;

    private FragmentWeatherBinding viewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseRepository databaseRepository = new DatabaseRepository(App.getInstance());

        presenter = new WeatherPresenter(this, this,
                new WeatherInteractor(
                        new WeatherNetworkInteractor(databaseRepository),
                        new WeatherDbInteractor(databaseRepository),
                        new UserLocationProvider()
                ));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        viewBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_weather, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = viewBinding.toolbar;
        toolbar.setTitle(R.string.weather);
        toolbar.inflateMenu(R.menu.menu);

        viewBinding.btnUpdate.setOnClickListener(v -> onClickForecast());
    }

    @Override
    public void showWeather(WeatherResponse weatherResponse) {
        viewBinding.setWeatherVM(new WeatherViewModel(weatherResponse));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(App.getInstance(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Log.wtf(LOG_TAG, "showLoading: ");

        viewBinding.setIsLoading(true);
    }

    @Override
    public void hideLoading() {
        Log.wtf(LOG_TAG, "hideLoading: ");

        viewBinding.setIsLoading(false);
    }

    public void onClickForecast() {
        presenter.getWeather();
    }
}
