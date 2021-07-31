package com.ifood.desafiobackend.domain.service;

import com.ifood.desafiobackend.domain.model.Temperature;
import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.model.Wind;
import com.ifood.desafiobackend.entrypoint.rest.CacheOpenWeatherApi;
import com.ifood.desafiobackend.entrypoint.rest.OpenWeatherApiFeignClient;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    private static final String WEATHER_DESCRIPTION = "Strong raining day";
    private static final String CITY = "São Paulo";
    private static final String TEMPERATURE = "233.93";
    private static final String TEMPERATURE_MIN = "224.93";
    private static final String TEMPERATURE_MAX = "240.00";
    private static final String FEELS_LIKE = "236.98";
    private static final String WIND_SPEED = "24.5";
    private static final String WIND_DEG = "21.0";
    private static final String WIND_GUST = "100";

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private OpenWeatherApiFeignClient openWeatherApiFeignClient;

    @Spy
    private CacheOpenWeatherApi cacheOpenWeatherApi;

    @Test
    void shouldMakeRequestAndReturnWeather() {
        // arrange
        final ResponseEntity<OpenWeatherReponseDTO> openWeatherResponse = ResponseEntity.ok();
        when(openWeatherApiFeignClient.getWeatherByCity(anyString(), anyString()))
                .thenReturn(openWeatherResponse);

        // act
        weatherService.findByCity(CITY);

        // arrange
    }

    private Weather getWeather() {

        final Temperature temperature = new Temperature(
                UUID.randomUUID(),
                TEMPERATURE,
                TEMPERATURE_MIN,
                TEMPERATURE_MAX,
                FEELS_LIKE,
                55
        );

        final Wind wind = new Wind(
                UUID.randomUUID(),
                WIND_SPEED,
                WIND_DEG,
                WIND_GUST
        );

        return Weather.WeatherBuilder.aWeather()
                .withId(UUID.randomUUID())
                .withWeatherDescription(WEATHER_DESCRIPTION)
                .withCityName(CITY)
                .withTemperature(temperature)
                .withWind(wind)
                .build();
    }
}
