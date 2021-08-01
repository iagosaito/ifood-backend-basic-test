package com.ifood.desafiobackend.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Weather {

    private UUID id;
    private String cityName;
    private Temperature temperature;
    private String weatherDescription;
    private Wind wind;
    private LocalDateTime updatedAt;

    public Weather(UUID id, String cityName, Temperature temperature, String weatherDescription, Wind wind, LocalDateTime updatedAt) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.wind = wind;
        this.updatedAt = updatedAt;
    }

    public Weather() {}

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static final class WeatherBuilder {
        private UUID id;
        private String cityName;
        private Temperature temperature;
        private String weatherDescription;
        private Wind wind;
        private LocalDateTime updatedAt = LocalDateTime.now();

        private WeatherBuilder() {
        }

        public static WeatherBuilder aWeather() {
            return new WeatherBuilder();
        }

        public WeatherBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public WeatherBuilder withCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public WeatherBuilder withTemperature(Temperature temperature) {
            this.temperature = temperature;
            return this;
        }

        public WeatherBuilder withWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
            return this;
        }

        public WeatherBuilder withWind(Wind wind) {
            this.wind = wind;
            return this;
        }

        public Weather build() {
            return new Weather(id, cityName, temperature, weatherDescription, wind, updatedAt);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(id, weather.id) && Objects.equals(cityName, weather.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityName);
    }
}
