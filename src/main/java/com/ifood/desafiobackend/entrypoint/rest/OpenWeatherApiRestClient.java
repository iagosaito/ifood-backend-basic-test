package com.ifood.desafiobackend.entrypoint.rest;

import com.ifood.desafiobackend.entrypoint.rest.dto.OpenWeatherReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherApi", url = "${openweathermap.api.url}")
public interface OpenWeatherApiRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    ResponseEntity<OpenWeatherReponseDTO> getWeatherByCity(@RequestParam(name = "q") final String city,
                                                           @RequestParam(name = "appid") final String apiKey);
}
