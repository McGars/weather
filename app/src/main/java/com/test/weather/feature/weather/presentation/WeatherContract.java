/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.weather.feature.weather.presentation;


import com.test.weather.domain.data.weahter.WeatherResponse;

public interface WeatherContract {

    interface View {

        void showWeather(WeatherResponse weatherResponse);

        void showError(String error);

        void showLoading();

        void hideLoading();

    }

    interface Presenter {

        void getWeather();

    }
}
