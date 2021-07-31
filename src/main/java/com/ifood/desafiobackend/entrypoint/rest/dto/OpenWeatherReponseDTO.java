package com.ifood.desafiobackend.entrypoint.rest.dto;

import java.util.List;
import java.util.StringJoiner;

public class OpenWeatherReponseDTO {

    private final String name;
    private final List<WeatherDTO> weather;
    private final MainDataDTO main;
    private final WindDTO wind;

    public OpenWeatherReponseDTO(String name, List<WeatherDTO> weather, MainDataDTO main, WindDTO wind) {
        this.name = name;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public List<WeatherDTO> getWeather() {
        return weather;
    }

    public MainDataDTO getMain() {
        return main;
    }

    public WindDTO getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenWeatherReponseDTO.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("weather=" + weather)
                .add("main=" + main)
                .add("wind=" + wind)
                .toString();
    }
}
