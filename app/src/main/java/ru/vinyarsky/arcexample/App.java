package ru.vinyarsky.arcexample;

import android.app.Application;

public final class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }


    public static AppComponent getComponent() {
        return appComponent;
    }
}
