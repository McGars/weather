package com.test.weather.feature.weather.model;

import com.test.weather.domain.data.weahter.WeatherResponse;

import java.io.Serializable;


public class WeatherViewModel implements Serializable {
    private String description;
    private String temp;
    private String tempMax;
    private String tempMin;
    private String humidity;
    private String pressure;
    private String name;
    private int conditionId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public WeatherViewModel(WeatherResponse weatherResponse) {
        if (weatherResponse.getWeather() != null) {
            if (weatherResponse.getWeather().get(0) != null
                    && weatherResponse.getWeather().get(0).getDescription() != null) {
                this.description = weatherResponse.getWeather().get(0).getDescription();
                this.conditionId = weatherResponse.getWeather().get(0).getId();
            }
        }
        if (weatherResponse.getMain() != null) {
            this.temp = String.valueOf(Double.valueOf(weatherResponse.getMain().getTemp()).intValue());
            this.tempMax = String.valueOf(weatherResponse.getMain().getTempMax());
            this.tempMin = String.valueOf(weatherResponse.getMain().getTempMin());
            this.humidity = String.valueOf(weatherResponse.getMain().getHumidity());
            this.pressure = String.valueOf(weatherResponse.getMain().getPressure());
        }
        if (weatherResponse.getName() != null) {
            this.name = weatherResponse.getName();
        }
    }

    @Override
    public String toString() {
        return "WeatherViewModel{" +
                " description='" + description + '\'' +
                ", temp='" + temp + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", name='" + name + '\'' +
                ", conditionId='" + conditionId + '\'' +
                '}';
    }
}
