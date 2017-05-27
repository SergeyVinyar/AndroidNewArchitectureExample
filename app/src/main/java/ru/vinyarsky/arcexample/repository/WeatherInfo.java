package ru.vinyarsky.arcexample.repository;

import ru.vinyarsky.arcexample.db.Weather;
import ru.vinyarsky.arcexample.network.WeatherResponseData;

public class WeatherInfo {

    private Status status;

    private String cityName;
    private String description;
    private String temperature;

    /* package */ WeatherInfo(Status status, Weather weather) {
        if (weather != null) {
            this.status = status;
            this.cityName = weather.cityName;
            this.description = weather.description;
            this.temperature = Double.toString(weather.temperature);
        }
        else {
            this.status = Status.NODATA;
            this.description = "No data loaded";
        }
    }

    public Status getStatus() {
        return status;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public String getTemperature() {
        return temperature;
    }
}

