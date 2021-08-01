package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.domain.model.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CacheOpenWeatherApi implements OpenWeatherApi {

    private static final Logger log = LogManager.getLogger(CacheOpenWeatherApi.class);
    private static final Map<String, Weather> CACHE_WEATHER_MAP = Collections.synchronizedMap(new HashMap<>());
    private static final int MAX_LIMIT_MINUTES_CACHE = 15;

    public static Map<String, Weather> getCacheWeatherMap() {
        return Collections.unmodifiableMap(CACHE_WEATHER_MAP);
    }

    @Override
    public Optional<Weather> getWeatherByCity(String city) {
        if (CACHE_WEATHER_MAP.containsKey(city)) {
            return Optional.of(CACHE_WEATHER_MAP.get(city));
        }

        return Optional.empty();
    }

    public void addWeather(final String city, final Weather weather) {
        CACHE_WEATHER_MAP.put(city, weather);
    }

    public void clearCache() {
        CACHE_WEATHER_MAP.clear();
    }

    public void cleanOutDatedCache(final LocalDateTime cleanCacheEventAt) {

        final HashMap<String, Weather> cachedWeatherMap = new HashMap<>(CACHE_WEATHER_MAP);

        for (Map.Entry<String, Weather> weatherEntry : cachedWeatherMap.entrySet()) {
            final LocalDateTime updatedAt = weatherEntry.getValue().getUpdatedAt();
            if (isCacheOutOfDate(cleanCacheEventAt, updatedAt)) {
                log.info("removing {} from cache", weatherEntry.getKey());
                CACHE_WEATHER_MAP.remove(weatherEntry.getKey(), weatherEntry.getValue());
            }
        }
    }

    private boolean isCacheOutOfDate(final LocalDateTime cleanEvent, final LocalDateTime lastUpdatedAt) {
        final long minutes = Math.abs(ChronoUnit.MINUTES.between(cleanEvent, lastUpdatedAt));
        return minutes >= MAX_LIMIT_MINUTES_CACHE;
    }
}
