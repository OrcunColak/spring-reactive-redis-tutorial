package com.colak.springreactiveredistutorial.stringvalue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class OpsForValueRepository<K,V> {

    private final ReactiveValueOperations<K, V> valueOperations;

    public Mono<V> getAndExpire(K key , Duration duration) {
        return valueOperations.getAndExpire(key,duration);
    }
    public Mono<V> get(String key) {
        return valueOperations.get(key);
    }

    public Mono<Boolean> set(K key, V value) {
        return valueOperations.set(key, value);
    }

    public Mono<Boolean> setIfAbsent(K key, V value) {
        return valueOperations.setIfAbsent(key, value);
    }

    public Mono<Long> increment(K key) {
        return valueOperations.increment(key);
    }
}
