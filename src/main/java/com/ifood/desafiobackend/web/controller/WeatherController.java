package com.ifood.desafiobackend.web.controller;

import com.ifood.desafiobackend.domain.model.Weather;
import com.ifood.desafiobackend.domain.service.WeatherService;
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
        final Weather weatherByCity = weatherService.findByCity(city);
        return ResponseEntity.ok(weatherByCity);
    }
}
