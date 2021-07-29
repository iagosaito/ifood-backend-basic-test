package com.ifood.desafiobackend.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Temperature {

    private UUID id;
    private String temperature;
    private String temperatureMin;
    private String temperatureMax;
    private String feelsLike;
    private Integer humidity;

    public Temperature(UUID id, String temperature, String temperatureMin, String temperatureMax, String feelsLike,
                       Integer humidity) {
        this.id = id;
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
    }

    public UUID getId() {
        return id;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public Integer getHumidity() {
        return humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
