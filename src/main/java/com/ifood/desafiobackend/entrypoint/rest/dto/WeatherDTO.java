package com.ifood.desafiobackend.entrypoint.rest.dto;

import java.util.StringJoiner;

public class WeatherDTO {

    private final String main;
    private final String description;
    private final String icon;

    public WeatherDTO(String main, String description, String icon) {
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WeatherDTO.class.getSimpleName() + "[", "]")
                .add("main='" + main + "'")
                .add("description='" + description + "'")
                .add("icon='" + icon + "'")
                .toString();
    }
}
