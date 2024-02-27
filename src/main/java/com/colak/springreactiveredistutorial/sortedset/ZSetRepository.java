package com.colak.springreactiveredistutorial.sortedset;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ZSetRepository<K, V> {
    private final K key;
    private final ReactiveZSetOperations<K, V> zSetOperations;

    public ZSetRepository(K key, ReactiveZSetOperations<K, V> zSetOperations) {
        this.key = key;
        this.zSetOperations = zSetOperations;
    }

    public Mono<Boolean> add(V value, double score) {
        return zSetOperations.add(key, value, score);
    }

    public Mono<Long> remove(V... values) {
        return zSetOperations.remove(key, values);
    }

    public Flux<V> rangeByScore(double min, double max) {
        Range<Double> range = Range.open(min, max);
        return zSetOperations.rangeByScore(key, range);
    }

}
