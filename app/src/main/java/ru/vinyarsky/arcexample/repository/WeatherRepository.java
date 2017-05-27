package ru.vinyarsky.arcexample.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ru.vinyarsky.arcexample.db.Weather;
import ru.vinyarsky.arcexample.db.WeatherDAO;
import ru.vinyarsky.arcexample.network.WeatherResponseData;
import ru.vinyarsky.arcexample.network.WebService;

@Singleton
public class WeatherRepository {

    private final String API_KEY = "INSERT_API_KEY_HERE"; // TODO Insert API key

    private final WeatherDAO weatherDao;
    private final WebService webService;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    public WeatherRepository(WeatherDAO weatherDao, WebService webService) {
        this.weatherDao = weatherDao;
        this.webService = webService;
    }

    public LiveData<List<String>> getCityList() {
        return weatherDao.loadCityList();
    }

    public LiveData<WeatherInfo> getWeather(final String cityName) {
        MediatorLiveData<WeatherInfo> result = new MediatorLiveData<>();

        LiveData<Weather> dbSource = weatherDao.load(cityName);
        result.addSource(dbSource, weather -> result.setValue(new WeatherInfo(Status.LOADING, weather)));

        this.webService.getWeather(cityName, API_KEY, "metric").enqueue(new Callback<WeatherResponseData>() {

            @Override
            public void onResponse(Call<WeatherResponseData> call, Response<WeatherResponseData> response) {
                WeatherResponseData body = response.body();
                Weather newWeather = new Weather(cityName, body.getMain().getTemp(), body.getWeather()[0].getMain());

                result.removeSource(dbSource);
                executorService.execute(() -> {
                    weatherDao.save(newWeather);
                    result.addSource(weatherDao.load(cityName), weather -> result.setValue(new WeatherInfo(Status.SUCCESS, weather)));
                });
            }

            @Override
            public void onFailure(Call<WeatherResponseData> call, Throwable t) {
                result.removeSource(dbSource);
                result.addSource(dbSource, weather -> result.setValue(new WeatherInfo(Status.ERROR, weather)));
            }
        });

        return result;
    }
}
