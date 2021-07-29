package com.ifood.desafiobackend.entrypoint.rest.dto;

import java.util.StringJoiner;

public class WindDTO {

    private final String speed;
    private final String deg;
    private final String gust;

    public WindDTO(String speed, String deg, String gust) {
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }

    public String getGust() {
        return gust;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WindDTO.class.getSimpleName() + "[", "]")
                .add("speed='" + speed + "'")
                .add("deg='" + deg + "'")
                .add("gust='" + gust + "'")
                .toString();
    }
}
