package com.ifood.desafiobackend.entrypoint.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@Configuration
public class Scheduler {

    @Async
    @Scheduled(fixedRate = 2000)
    public void cleanOutDateWeatherInCache() {
        System.out.println("print...");
    }
}
