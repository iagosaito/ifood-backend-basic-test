package com.ifood.desafiobackend.domain.model.converter;

import java.util.UUID;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;

public class WeatherConverter {

    public static Weather toEntity(final OpenWeatherReponseDTO openWeatherReponseDTO) {

        final Weather weather = Weather.WeatherBuilder.aWeather()
                .withCityName(openWeatherReponseDTO.getName())
                .withWeatherDescription(openWeatherReponseDTO.getWeather()
                .build();
    }
}
