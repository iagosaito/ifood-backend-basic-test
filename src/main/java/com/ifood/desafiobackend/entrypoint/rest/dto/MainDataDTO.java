package com.ifood.desafiobackend.entrypoint.rest.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MainDataDTO {

    private final String temp;
    private final String feelsLike;
    private final String tempMin;
    private final String tempMax;
    private final Integer pressure;
    private final Integer humidity;

    public MainDataDTO(String temp, String feelsLike, String tempMin, String tempMax, Integer pressure,
                       Integer humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getTemp() {
        return temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }
}
