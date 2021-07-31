package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.domain.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheOpenWeatherApiTest {

    private static final String LONDON_CITY = "London";
    private static final String SAO_PAULO_CITY = "São Paulo";
    private static final String NEY_YORK_CITY = "NY";

    private final CacheOpenWeatherApi cacheOpenWeatherApi = new CacheOpenWeatherApi();

    @BeforeEach
    void setUp() {
        cacheOpenWeatherApi.addWeather(LONDON_CITY, new Weather());
        cacheOpenWeatherApi.addWeather(SAO_PAULO_CITY, new Weather());
        cacheOpenWeatherApi.addWeather(NEY_YORK_CITY, new Weather());
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

}
