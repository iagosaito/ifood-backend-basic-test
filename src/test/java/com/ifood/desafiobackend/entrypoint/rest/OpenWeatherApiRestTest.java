package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class OpenWeatherApiRestTest {

    @Autowired
    private OpenWeatherApiRestClient openWeatherApiRestClient;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Test
    void shouldGetWeatherByCity() {
        final ResponseEntity<OpenWeatherReponseDTO> weatherInLondon = openWeatherApiRestClient.getWeatherByCity("London", apiKey);
        Assertions.assertThat(weatherInLondon.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
