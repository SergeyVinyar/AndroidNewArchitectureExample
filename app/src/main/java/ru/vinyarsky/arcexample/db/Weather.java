package ru.vinyarsky.arcexample.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Weather {

    public Weather(String cityName, double temperature, String description) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.description = description;
    }

    @PrimaryKey
    public String cityName;

    public double temperature;

    public String description;
}
