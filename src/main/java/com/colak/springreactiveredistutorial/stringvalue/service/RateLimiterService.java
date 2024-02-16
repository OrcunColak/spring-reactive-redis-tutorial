package com.colak.springreactiveredistutorial.stringvalue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    private static final String KEY_PREFIX = "rate-limiter";

    private static String getKey(String userId) {
        return KEY_PREFIX + ":" + userId;
    }

    public static String getCountKey(String userId) {
        return getKey(userId) + ":count";
    }

    public static String getTimestampKey(String userId) {
        return getKey(userId) + ":timestamp";
    }

    public Mono<Boolean> tryAcquire(String userId, int capacity, Duration refillInterval) {
        String countKey = getCountKey(userId);
        String timestampKey = getTimestampKey(userId);

        return reactiveStringRedisTemplate
                .opsForValue()
                .increment(countKey)
                .flatMap(count -> {
                    if (count <= capacity) {
                        if (count == 1) {
                            return reactiveStringRedisTemplate.opsForValue()
                                    .set(timestampKey, String.valueOf(System.currentTimeMillis()));
                        } else {
                            return reactiveStringRedisTemplate
                                    .opsForValue()
                                    .get(timestampKey)
                                    .flatMap(timestamp -> {
                                        long currentTime = System.currentTimeMillis();
                                        long storedTime = Long.parseLong(timestamp);
                                        long elapsedTime = currentTime - storedTime;

                                        if (elapsedTime > refillInterval.toMillis()) {
                                            return reactiveStringRedisTemplate.opsForValue()
                                                    .set(timestampKey, String.valueOf(System.currentTimeMillis()))
                                                    .map(__ -> true);

                                        } else {
                                            return Mono.just(false);
                                        }

                                    });
                        }
                    } else {
                        return Mono.empty();
                    }
                })
                .filter(allowed -> allowed)
                .flatMap(__ -> reactiveStringRedisTemplate.expire(countKey, refillInterval));
    }
}
