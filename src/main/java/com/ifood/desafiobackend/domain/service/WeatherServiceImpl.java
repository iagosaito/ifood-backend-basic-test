package com.ifood.desafiobackend.domain.service;

import java.util.Objects;
import java.util.Optional;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.model.converter.WeatherConverter;
import com.ifood.desafiobackend.entrypoint.rest.CacheOpenWeatherApi;
import com.ifood.desafiobackend.entrypoint.rest.OpenWeatherApiFeignClient;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public final class WeatherServiceImpl implements WeatherService {

    private static final Logger log = LogManager.getLogger(WeatherServiceImpl.class);

    private final OpenWeatherApiFeignClient openWeatherApiFeignClient;
    private final CacheOpenWeatherApi cacheOpenWeatherApi;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherServiceImpl(final OpenWeatherApiFeignClient openWeatherApiFeignClient,
                              final CacheOpenWeatherApi cacheOpenWeatherApi1) {
        this.openWeatherApiFeignClient = openWeatherApiFeignClient;
        this.cacheOpenWeatherApi = cacheOpenWeatherApi1;
    }

    @Override
    public Weather findByCity(final String city) {

        if (!StringUtils.hasText(city)) {
            throw new IllegalArgumentException("'city' cannot be null");
        }

        final Optional<Weather> cachedWeather = cacheOpenWeatherApi.getWeatherByCity(city);

        if (cachedWeather.isPresent()) {
            log.info("city {} found in cache", city);
            return cachedWeather.get();
        }

        log.info("requesting {} weather to OpenWeatherApi...", city);
        final ResponseEntity<OpenWeatherReponseDTO> responseWeatherApi =
                openWeatherApiFeignClient.getWeatherByCity(city, apiKey);

        if (responseWeatherApi.getStatusCode().is2xxSuccessful()) {
            final Weather weather = WeatherConverter.toEntity(Objects.requireNonNull(responseWeatherApi.getBody()));
            cacheOpenWeatherApi.addWeather(city, weather);
            return weather;
        }

        throw new RuntimeException(); // TODO: Corrigir para exception correta
    }
}
