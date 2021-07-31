package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.domain.model.Weather;

import java.util.Optional;

public interface OpenWeatherApi {

    Optional<Weather> getWeatherByCity(final String city);
}
