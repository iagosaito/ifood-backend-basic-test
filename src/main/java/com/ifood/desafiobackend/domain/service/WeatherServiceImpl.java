package com.ifood.desafiobackend.domain.service;

import com.ifood.desafiobackend.entrypoint.rest.OpenWeatherApiRestClient;
import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public final class WeatherServiceImpl implements WeatherService {

    private final OpenWeatherApiRestClient openWeatherApiRestClient;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherServiceImpl(final OpenWeatherApiRestClient openWeatherApiRestClient) {
        this.openWeatherApiRestClient = openWeatherApiRestClient;
    }

    @Override
    public String findByCity(final String city) {
        final ResponseEntity<OpenWeatherReponseDTO> responseWeatherApi =
                openWeatherApiRestClient.getWeatherByCity(city, apiKey);

        if (responseWeatherApi.getStatusCode().is2xxSuccessful()) {
            System.out.println(responseWeatherApi.getBody());
        }

        return null;
    }
}
