package ru.vinyarsky.arcexample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Weather.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDAO weatherDao();
}
