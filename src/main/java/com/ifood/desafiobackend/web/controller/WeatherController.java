package com.ifood.desafiobackend.web.controller;

import java.time.Duration;
import java.util.function.Supplier;

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
    private final CircuitBreaker circuitBreaker;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;

        CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(4)
                .slowCallRateThreshold(50.0f)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        this.circuitBreaker = registry.circuitBreaker("weatherservice");
    }

    @GetMapping
    public ResponseEntity<Weather> getWeatherByCity(@RequestParam String city) {

        try {
            final Supplier<Weather> weatherSupplier = circuitBreaker.decorateSupplier(() ->
                    weatherService.findByCity(city));

            final Weather weather = weatherSupplier.get();

            return ResponseEntity.ok(weather);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}