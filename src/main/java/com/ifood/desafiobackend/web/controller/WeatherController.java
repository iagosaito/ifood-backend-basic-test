package com.ifood.desafiobackend.web.controller;

import java.util.function.Supplier;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.service.WeatherService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CircuitBreaker circuitBreaker;

    public WeatherController(WeatherService weatherService, CircuitBreaker circuitBreaker) {
        this.weatherService = weatherService;
        this.circuitBreaker = circuitBreaker;
    }

    @GetMapping
    public ResponseEntity<Weather> getWeatherByCity(@RequestParam String city) {

        try {
            final Supplier<Weather> weatherSupplier = circuitBreaker.decorateSupplier(() ->
                    weatherService.findByCity(city));

            final Weather weather = weatherSupplier.get();

            return ResponseEntity.ok(weather);
        } catch (CallNotPermittedException ex) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "OpenWeatherApi is unavailable... " +
                    "try again later");
        }
    }
}