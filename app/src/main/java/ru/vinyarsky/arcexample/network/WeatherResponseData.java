package ru.vinyarsky.arcexample.network;

/**
 * Структура, возвращаемая web-сервисом
 */
public final class WeatherResponseData {

    private WeatherData[] weather;
    private MainData main;

    public WeatherData[] getWeather() {
        return weather;
    }

    public MainData getMain() {
        return main;
    }

    public final class WeatherData {
        String main;

        /**
         * @return Текстовое описание погоды
         */
        public String getMain() {
            return main;
        }
    }

    public final class MainData {
        double temp;

        /**
         * @return Температура
         */
        public double getTemp() {
            return temp;
        }
    }
}
