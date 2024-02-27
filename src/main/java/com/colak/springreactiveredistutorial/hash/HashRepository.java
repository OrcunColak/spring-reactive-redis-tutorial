package com.colak.springreactiveredistutorial.hash;

import org.springframework.data.redis.core.ReactiveHashOperations;
import reactor.core.publisher.Mono;

public class HashRepository<K, V> {

    private final String key;

    private final ReactiveHashOperations<String, K, V> hashOperations;

    public HashRepository(String key, ReactiveHashOperations<String, K, V> hashOperations) {
        this.key = key;
        this.hashOperations = hashOperations;
    }

    public Mono<V> get(K hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public Mono<Boolean> put(K hashKey, V value) {
        return hashOperations.put(key, hashKey, value);
    }

    public Mono<Long> remove(K hashKey) {
        return hashOperations.remove(key, hashKey);
    }
}
