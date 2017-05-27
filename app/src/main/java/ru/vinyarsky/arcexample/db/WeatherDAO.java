package ru.vinyarsky.arcexample.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT cityName FROM Weather")
    LiveData<List<String>> loadCityList();

    @Query("SELECT * FROM Weather WHERE cityName = :cityName LIMIT 1")
    LiveData<Weather> load(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Weather weather);
}
