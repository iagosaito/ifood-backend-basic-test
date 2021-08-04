package com.ifood.desafiobackend.web.controller;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.service.WeatherService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<Weather> getWeatherByCity(@RequestParam String city) {

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig defaultConfig = circuitBreakerRegistry.getDefaultConfig();

        CircuitBreakerConfig overwrittenConfig = CircuitBreakerConfig
                .from(defaultConfig)
                .failureRateThreshold(50)
                .minimumNumberOfCalls(4)
                .build();

        final CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("app", overwrittenConfig);

        final Weather weather = CircuitBreaker.decorateSupplier(circuitBreaker, () -> weatherService.findByCity(city))
                .get();

        return ResponseEntity.ok(weather);
    }
}