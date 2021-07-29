package com.ifood.desafiobackend.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Weather {

    private UUID id;
    private String cityName;
    private Temperature temperature;
    private String weatherDescription;
    private Wind wind;

    public Weather(UUID id, String cityName, Temperature temperature, String weatherDescription, Wind wind) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.wind = wind;
    }

    public UUID getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public Wind getWind() {
        return wind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(id, weather.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
