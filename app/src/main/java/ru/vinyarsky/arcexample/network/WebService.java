package ru.vinyarsky.arcexample.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("/data/2.5/weather")
    public Call<WeatherResponseData> getWeather(@Query("q") String cityName, @Query("APPID") String apiKey, @Query("units") String units);
}
