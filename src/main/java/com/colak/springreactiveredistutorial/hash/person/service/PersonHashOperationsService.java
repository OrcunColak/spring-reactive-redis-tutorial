package com.colak.springreactiveredistutorial.hash.person.service;

import com.colak.springreactiveredistutorial.hash.person.model.Person;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonHashOperationsService {

    private static final String KEY = "person";

    private final ReactiveHashOperations<String, Long, Person> hashOperations;

    public PersonHashOperationsService(ReactiveRedisTemplate<String, Person> personRedisTemplate) {
        hashOperations = personRedisTemplate.opsForHash();
    }

    public Mono<Person> get(Long id) {
        return hashOperations.get(KEY, id);
    }

    public Mono<Boolean> put(Person person) {
        return hashOperations.put(KEY, person.getId(), person);
    }

    public Mono<Long> remove(Long id) {
        return hashOperations.remove(KEY, id);
    }
}
