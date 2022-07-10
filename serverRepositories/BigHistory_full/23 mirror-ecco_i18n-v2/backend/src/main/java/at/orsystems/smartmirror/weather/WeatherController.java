package at.orsystems.smartmirror.weather;

import at.orsystems.smartmirror.weather.dto.CurrentWeatherDTO;
import at.orsystems.smartmirror.weather.dto.WeatherForecastDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Ratzenböck
 * @since 2020
 */
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public CurrentWeatherDTO weather(@RequestParam("cityName") final String cityName) {
        return weatherService.getCurrentWeather(cityName);
    }

    @GetMapping("/weatherForecast")
    public WeatherForecastDTO weatherForecast(@RequestParam("cityName") final String cityName) {
        final var weatherForecast = weatherService.getWeatherForecast(cityName);
        System.err.println(weatherForecast);
        return weatherForecast;
    }
}
