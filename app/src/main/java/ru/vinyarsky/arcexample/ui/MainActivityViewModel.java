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
    private MutableLiveData<Object> showCityEvent;

    private final LiveData<WeatherInfo> weatherInfoLiveData;

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

    public LiveData<Object> showCity() {
        showCityEvent = new MutableLiveData<>();
        return showCityEvent;
    }

    public void selectCity(String cityName) {
        cityNameLiveData.setValue(cityName);
        if (showCityEvent != null)
            showCityEvent.setValue(null);
    }

    public void refreshData() {
        cityNameLiveData.setValue(cityNameLiveData.getValue());
    }
}
