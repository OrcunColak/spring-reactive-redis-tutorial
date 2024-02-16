package com.colak.springreactiveredistutorial.stringvalue.opsforvalue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class OpsForValueService <T> {

    private final ReactiveRedisTemplate<String, T> reactiveRedisTemplate;

    public Mono<T> getAndExpire(String key , Duration duration) {
        return reactiveRedisTemplate.opsForValue().getAndExpire(key,duration);
    }
    public Mono<T> get(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> set(String key, T value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }

    public Mono<Boolean> setIfAbsent(String key, T value) {
        return reactiveRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Mono<Long> increment(String key) {
        return reactiveRedisTemplate.opsForValue().increment(key);
    }
}
