package com.ifood.desafiobackend.domain.service;

import com.ifood.desafiobackend.domain.model.Weather;

public interface WeatherService {

    Weather findByCity(final String city);

}
