package com.ifood.desafiobackend.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.model.converter.WeatherConverter;
import com.ifood.desafiobackend.entrypoint.rest.CacheOpenWeatherApi;
import com.ifood.desafiobackend.entrypoint.rest.OpenWeatherApiFeignClient;
import com.ifood.desafiobackend.entrypoint.rest.dto.MainDataDTO;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import com.ifood.desafiobackend.entrypoint.rest.dto.WeatherDTO;
import com.ifood.desafiobackend.entrypoint.rest.dto.WindDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

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
    private static final int PRESSURE = 100;
    private static final int HUMIDITY = 35;
    private static final String MAIN_WEATHER_DESCRIPTION = "Rain";
    private static final String ICON = "icon";
    private static final String EXCEPTION_MESSAGE = "'city' cannot be null";

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private OpenWeatherApiFeignClient openWeatherApiFeignClient;

    @Spy
    private CacheOpenWeatherApi cacheOpenWeatherApi;

    @BeforeEach
    void setUp() {
        cacheOpenWeatherApi.clearCache();
    }

    @Test
    void shouldMakeRequestAndReturnWeather() {
        // arrange
        final var openWeatherResponse = ResponseEntity.ok(getWeatherResponseDTO());
        when(openWeatherApiFeignClient.getWeatherByCity(anyString(), any())).thenReturn(openWeatherResponse);
        final Weather expectedWeather = WeatherConverter.toEntity(getWeatherResponseDTO());

        // act
        final Weather weatherFound = weatherService.findByCity(CITY);

        // assert
        assertThat(weatherFound)
                .usingRecursiveComparison()
                .ignoringFields("id", "updatedAt", "wind.id", "temperature.id")
                .isEqualTo(expectedWeather);

        verify(cacheOpenWeatherApi, times(1)).addWeather(CITY, weatherFound);
    }

    @Test
    void shouldReturnCachedWeather() {
        // arrange
        final Weather expectedWeather = WeatherConverter.toEntity(getWeatherResponseDTO());
        new CacheOpenWeatherApi().addWeather(CITY, expectedWeather);

        // act
        weatherService.findByCity(CITY);

        // assert
        verify(openWeatherApiFeignClient, never()).getWeatherByCity(any(), any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowExceptionWhenCityIsNullOrEmpty(final String city) {

        // arrange | act
        final Throwable exception = catchThrowable(() -> weatherService.findByCity(city));

        // assert
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EXCEPTION_MESSAGE);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNot2xx() {
        // arrange
        final ResponseEntity<OpenWeatherReponseDTO> response = ResponseEntity.notFound().build();
        when(openWeatherApiFeignClient.getWeatherByCity(anyString(), any())).thenReturn(response);

        // act
        final Throwable exception = catchThrowable(() -> weatherService.findByCity(CITY));

        // assert
        assertThat(exception)
                .isInstanceOf(RuntimeException.class);

        verify(cacheOpenWeatherApi, never()).addWeather(any(), any());
    }

    private OpenWeatherReponseDTO getWeatherResponseDTO() {

        final WeatherDTO weatherDTO = new WeatherDTO(
                MAIN_WEATHER_DESCRIPTION,
                WEATHER_DESCRIPTION,
                ICON
        );

        final MainDataDTO mainDataDTO = new MainDataDTO(
                TEMPERATURE,
                FEELS_LIKE,
                TEMPERATURE_MIN,
                TEMPERATURE_MAX,
                PRESSURE,
                HUMIDITY
        );

        final WindDTO windDTO = new WindDTO(
                WIND_SPEED,
                WIND_GUST,
                WIND_DEG
        );

        return new OpenWeatherReponseDTO(
                "São Paulo",
                Collections.singletonList(weatherDTO),
                mainDataDTO,
                windDTO
        );
    }
}
