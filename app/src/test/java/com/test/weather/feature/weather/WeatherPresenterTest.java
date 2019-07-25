package com.test.weather.feature.weather;

import com.google.gson.Gson;

import com.test.weather.domain.data.weahter.WeatherResponse;
import com.test.weather.domain.interactor.WeatherInteractor;
import com.test.weather.feature.weather.presentation.WeatherContract;
import com.test.weather.feature.weather.presentation.presenter.WeatherPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class WeatherPresenterTest implements LifecycleOwner {

    @Mock
    private WeatherContract.View weatherView;

    @Mock
    private WeatherInteractor repository;

    @Captor
    private ArgumentCaptor<WeatherInteractor.RepositoryServiceCallback<WeatherResponse>> repositoryCallback;

    private WeatherPresenter mWeatherPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mWeatherPresenter = new WeatherPresenter(this, weatherView, repository);
    }

    @Test
    public void loadForecast_FromNetwork_andSucceeded() throws Exception {
        WeatherResponse response = generateWeatherResponse();

        mWeatherPresenter.getWeather();
        verify(weatherView).showLoading();
        verify(repository).getWeather(repositoryCallback.capture());

        repositoryCallback.getValue().onResult(response);

        verify(weatherView).hideLoading();
        verify(weatherView).showWeather(response);
    }

    @Test
    public void loadWeather_FromDatabase_andSucceeded() throws Exception {
        WeatherResponse response = null;//generateWeatherResponse();

        mWeatherPresenter.getWeather();
        verify(weatherView).showLoading();
        verify(repository).getWeather(repositoryCallback.capture());

        repositoryCallback.getValue().onResult(response);

        verify(weatherView).hideLoading();
        verify(weatherView).showWeather(response);
    }

    @Test
    public void loadForecast_andFailed() throws Exception {
        String testError = "error";

        mWeatherPresenter.getWeather();
        verify(weatherView).showLoading();
        verify(repository).getWeather(repositoryCallback.capture());

        repositoryCallback.getValue().onError(testError);

        verify(weatherView).hideLoading();
        verify(weatherView).showError(testError);
    }

    @Test
    public void loadForecast_onNoConnectionAndEmptyDb_andShowMessage() throws Exception {
        mWeatherPresenter.getWeather();
        verify(weatherView).showLoading();
        verify(repository).getWeather(repositoryCallback.capture());

        repositoryCallback.getValue().onError("");

        verify(weatherView).hideLoading();
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mWeatherPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(weatherView);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return new LifecycleRegistry(this);
    }

    private WeatherResponse generateWeatherResponse() {
        String response = "{\"coord\":{\"lon\":43.99,\"lat\":56.33},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":16.77,\"pressure\":1022,\"humidity\":59,\"temp_min\":16.67,\"temp_max\":17},\"visibility\":10000,\"wind\":{\"speed\":3,\"deg\":260},\"clouds\":{\"all\":0},\"dt\":1559775747,\"sys\":{\"type\":1,\"id\":9037,\"message\":0.0051,\"country\":\"RU\",\"sunrise\":1559780373,\"sunset\":1559843160},\"timezone\":10800,\"id\":520555,\"name\":\"Nizhniy Novgorod\",\"cod\":200}";

        Gson gson = new Gson();
        WeatherResponse weatherResponse = gson.fromJson(response, WeatherResponse.class);

        return weatherResponse;
    }

}
