package com.ifood.desafiobackend.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Wind {

    private UUID id;
    private String speed;
    private String deg;
    private String gust;

    public Wind(UUID id, String speed, String deg, String gust) {
        this.id = id;
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public UUID getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wind wind = (Wind) o;
        return Objects.equals(id, wind.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
