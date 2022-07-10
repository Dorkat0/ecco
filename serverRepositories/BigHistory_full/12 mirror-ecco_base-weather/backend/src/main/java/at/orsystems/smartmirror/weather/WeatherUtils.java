package at.orsystems.smartmirror.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;

/**
 * @author Michael Ratzenböck
 * @since 2020
 */
@Component
final class WeatherUtils {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/%s/weather?q=%s&units=%s&appid=%s&lang=%s";

    private static String openWeatherMapAPIKey;
    private static String openWeatherMapVersion;
    private static String openWeatherMapUnits;
    private static String openWeatherMapLanguage;

    @Value("${openweathermap.version}")
    private String tOpenWeatherMapVersion;
    @Value("${openweathermap.units}")
    private String tOpenWeatherMapUnits;
    @Value("${openweathermap.API}")
    private String tOpenWeatherMapAPIKey;
    @Value("${openweathermap.language}")
    private String tOpenWeatherMapLanguage;

    private WeatherUtils() {
    }

    public static String getCurrentWeatherUrl(@NonNull String cityName) {
        requireNonNull(cityName);
        return String.format(WEATHER_URL,
                openWeatherMapVersion,
                cityName,
                openWeatherMapUnits,
                openWeatherMapAPIKey,
                openWeatherMapLanguage);
    }

    public static String getForecastUrl(@NonNull String cityName) {
        return "";
    }

    @PostConstruct
    public void init() {
        WeatherUtils.openWeatherMapAPIKey = tOpenWeatherMapAPIKey;
        WeatherUtils.openWeatherMapUnits = tOpenWeatherMapUnits;
        WeatherUtils.openWeatherMapVersion = tOpenWeatherMapVersion;
        WeatherUtils.openWeatherMapLanguage = tOpenWeatherMapLanguage;
    }
}
