package com.ifood.desafiobackend.entrypoint.job;

import com.ifood.desafiobackend.entrypoint.rest.CacheOpenWeatherApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@EnableAsync
@Configuration
public class Scheduler {

    private static final Logger log = LogManager.getLogger(Scheduler.class);

    @Autowired
    private CacheOpenWeatherApi cacheOpenWeatherApi;

    @Async
    @Scheduled(fixedRate = 2000)
    public void cleanOutDateWeatherInCache() {
        final LocalDateTime cleanCacheEventAt = LocalDateTime.now();
        log.info("running cleaning cache job...");
        cacheOpenWeatherApi.cleanOutDatedCache(cleanCacheEventAt);
    }
}
