package com.ifood.desafiobackend.domain.model.converter;

import java.util.UUID;

import com.ifood.desafiobackend.domain.model.Temperature;
import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.model.Wind;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;

public class WeatherConverter {

    public static Weather toEntity(final OpenWeatherReponseDTO openWeatherReponseDTO) {

        final UUID id = UUID.randomUUID();

        final Temperature temperature = new Temperature(
                id,
                openWeatherReponseDTO.getMain().getTemp(),
                openWeatherReponseDTO.getMain().getTempMin(),
                openWeatherReponseDTO.getMain().getTempMax(),
                openWeatherReponseDTO.getMain().getFeelsLike(),
                openWeatherReponseDTO.getMain().getHumidity()
        );

        final Wind wind = new Wind(
                id,
                openWeatherReponseDTO.getWind().getSpeed(),
                openWeatherReponseDTO.getWind().getDeg(),
                openWeatherReponseDTO.getWind().getGust()
        );

        return Weather.WeatherBuilder.aWeather()
                .withId(id)
                .withCityName(openWeatherReponseDTO.getName())
                .withWeatherDescription(openWeatherReponseDTO.getWeather().get(0).getDescription())
                .withTemperature(temperature)
                .withWind(wind)
                .build();
    }
}
