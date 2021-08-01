package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.domain.model.Weather;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheOpenWeatherApiTest {

    private static final String LONDON_CITY = "London";
    private static final String SAO_PAULO_CITY = "São Paulo";
    private static final String NEY_YORK_CITY = "NY";

    private final CacheOpenWeatherApi cacheOpenWeatherApi = new CacheOpenWeatherApi();

    @BeforeEach
    void setUp() {
        final Weather weather = Weather.WeatherBuilder.aWeather().build();
        cacheOpenWeatherApi.addWeather(LONDON_CITY, weather);
        cacheOpenWeatherApi.addWeather(SAO_PAULO_CITY, weather);
        cacheOpenWeatherApi.addWeather(NEY_YORK_CITY, weather);
    }

    @AfterEach
    void cleanCache() {
        cacheOpenWeatherApi.clearCache();
    }

    @Test
    void shouldFoundWeatherInCache() {
        final Optional<Weather> londonWeather = cacheOpenWeatherApi.getWeatherByCity(LONDON_CITY);
        final Optional<Weather> saoPauloWeather = cacheOpenWeatherApi.getWeatherByCity(SAO_PAULO_CITY);
        final Optional<Weather> newYorkWeather = cacheOpenWeatherApi.getWeatherByCity(NEY_YORK_CITY);

        assertThat(londonWeather.isPresent()).isTrue();
        assertThat(saoPauloWeather.isPresent()).isTrue();
        assertThat(newYorkWeather.isPresent()).isTrue();
    }

    @Test
    void shouldNotFoundWeatherInCache() {
        final Optional<Weather> tokioWeather = cacheOpenWeatherApi.getWeatherByCity("Tókio");
        assertThat(tokioWeather.isPresent()).isFalse();
    }

    @Test
    void shouldClearWeatherInCacheOlderThanMaxLimit() {

        // arrange
        final byte numberOfCitiesInCacheExpected = 3;
        final Weather weather = new Weather();
        weather.setUpdatedAt(LocalDateTime.now().minusMinutes(15));
        cacheOpenWeatherApi.addWeather("RIO_DE_JANEIRO", weather);

        // act
        cacheOpenWeatherApi.cleanOutDatedCache(LocalDateTime.now());

        // assert
        assertThat(CacheOpenWeatherApi.getCacheWeatherMap().size()).isEqualTo(numberOfCitiesInCacheExpected);
    }

    @Test
    void shouldNotClearWeatherInCacheLessThanMaxLimit() {
        // arrange
        final byte numberOfCitiesInCacheExpected = 4;
        final Weather weather = new Weather();
        weather.setUpdatedAt(LocalDateTime.now().minusMinutes(14));
        cacheOpenWeatherApi.addWeather("RIO_DE_JANEIRO", weather);

        // act
        cacheOpenWeatherApi.cleanOutDatedCache(LocalDateTime.now());

        // assert
        assertThat(CacheOpenWeatherApi.getCacheWeatherMap().size()).isEqualTo(numberOfCitiesInCacheExpected);
    }

}
