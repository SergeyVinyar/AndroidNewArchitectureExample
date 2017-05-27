package ru.vinyarsky.arcexample.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ru.vinyarsky.arcexample.App;
import ru.vinyarsky.arcexample.repository.WeatherInfo;
import ru.vinyarsky.arcexample.repository.WeatherRepository;

public class MainActivityViewModel extends ViewModel {

    @Inject
    public WeatherRepository weatherRepository;

    private final MutableLiveData<String> cityNameLiveData = new MutableLiveData<>();

    private final LiveData<WeatherInfo> weatherInfoLiveData;

    private Runnable doShowCity;

    @Inject
    public MainActivityViewModel() {
        App.getComponent().inject(this);
        this.weatherInfoLiveData = Transformations.switchMap(cityNameLiveData, weatherRepository::getWeather);
    }

    public LiveData<List<String>> listOfCities() {
        return weatherRepository.getCityList();
    }

    public LiveData<WeatherInfo> weatherInfo() {
        return weatherInfoLiveData;
    }

    public void selectCity(String cityName) {
        cityNameLiveData.setValue(cityName);
        doShowCity.run();
    }

    public void refreshData() {
        cityNameLiveData.setValue(cityNameLiveData.getValue());
    }

    public void onShowCity(Runnable action) {
        doShowCity = action;
    }
}
