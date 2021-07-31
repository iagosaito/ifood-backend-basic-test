package com.ifood.desafiobackend.entrypoint.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ifood.desafiobackend.domain.model.Weather;
import org.springframework.stereotype.Component;

@Component
public class CacheOpenWeatherApi implements OpenWeatherApi {

    private static final Map<String, Weather> cachedRequestWeather = new HashMap<>();

    @Override
    public Optional<Weather> getWeatherByCity(String city) {
        if (cachedRequestWeather.containsKey(city)) {
            return Optional.of(cachedRequestWeather.get(city));
        }

        return Optional.empty();
    }

    public void addWeather(final String city, final Weather weather) {
        cachedRequestWeather.put(city, weather);
    }

    public void clearCache() {
        cachedRequestWeather.clear();
    }
}
