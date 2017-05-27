package ru.vinyarsky.arcexample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vinyarsky.arcexample.db.AppDatabase;
import ru.vinyarsky.arcexample.db.Weather;
import ru.vinyarsky.arcexample.db.WeatherDAO;
import ru.vinyarsky.arcexample.network.WebService;
import ru.vinyarsky.arcexample.repository.WeatherRepository;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    WeatherDAO provideWeatherDAO() {
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "data")
                .addMigrations(MIGRATION_1_2)
                .build();

        // TODO
        Executors.newSingleThreadExecutor().execute(() -> {
            init(database.weatherDao());
        });

        return database.weatherDao();
    }

    void init(WeatherDAO weatherDao) {
        if (weatherDao.load("Moscow").getValue() == null)
            weatherDao.save(new Weather("Moscow", 0, null));
        if (weatherDao.load("London").getValue() == null)
            weatherDao.save(new Weather("London", 0, null));
        if (weatherDao.load("Berlin").getValue() == null)
            weatherDao.save(new Weather("Berlin", 0, null));
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.beginTransaction();
            try {
                database.execSQL("INSERT INTO 'Weather' (CityName, Temperature, Description) VALUES ('Moscow', NULL, NULL)");
                database.execSQL("INSERT INTO 'Weather' (CityName, Temperature, Description) VALUES ('London', NULL, NULL)");
                database.execSQL("INSERT INTO 'Weather' (CityName, Temperature, Description) VALUES ('Berlin', NULL, NULL)");
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
    };

    @Provides
    @Singleton
    WebService provideWebService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebService.class);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherDAO weatherDAO, WebService webService) {
        return new WeatherRepository(weatherDAO, webService);
    }
}
