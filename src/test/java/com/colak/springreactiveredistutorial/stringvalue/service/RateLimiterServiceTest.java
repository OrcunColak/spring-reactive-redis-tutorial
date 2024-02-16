package com.colak.springreactiveredistutorial.stringvalue.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.time.Duration;

@SpringBootTest
@Slf4j
class RateLimiterServiceTest {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Test
    void testTryAcquire() {
        String key = "test:key";
        int limit = 2;

        String countKey = RateLimiterService.getCountKey(key);
        Long deletedKeys = reactiveStringRedisTemplate
                .delete(countKey)
                .blockOptional()
                .orElse(-1L);

        String timestampKey = RateLimiterService.getTimestampKey(key);
        deletedKeys = reactiveStringRedisTemplate.delete(timestampKey)
                .blockOptional()
                .orElse(-1L);


        Duration duration = Duration.ofSeconds(30);
        Boolean result1 = rateLimiterService.tryAcquire(key, limit, duration)
                .blockOptional()
                .orElse(false);
        log.info("Result 1 : {}" , result1);

        Boolean result2 = rateLimiterService.tryAcquire(key, limit, duration)
                .blockOptional()
                .orElse(false);
        log.info("Result 2 : {}" , result2);

        // // Scenario 1: Requests below the limit should be allowed
        // StepVerifier.create(rateLimiterService.tryAcquire(key, limit, duration))
        //         .expectNext(true)
        //         .verifyComplete();
        //
        //
        // StepVerifier.create(rateLimiterService.tryAcquire(key, limit, duration))
        //         .expectNext(true)
        //         .verifyComplete();


    }
}
