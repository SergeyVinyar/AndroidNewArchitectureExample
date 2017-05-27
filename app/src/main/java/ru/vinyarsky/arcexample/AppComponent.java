package ru.vinyarsky.arcexample;

import javax.inject.Singleton;

import dagger.Component;
import ru.vinyarsky.arcexample.db.WeatherDAO;
import ru.vinyarsky.arcexample.network.WebService;
import ru.vinyarsky.arcexample.repository.WeatherRepository;
import ru.vinyarsky.arcexample.ui.MainActivityViewModel;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    WeatherDAO weatherDao();
    WebService webService();

    WeatherRepository weatherRepository();

    void inject(MainActivityViewModel mainActivityViewModel);
    void inject(WeatherRepository weatherRepository);
}
