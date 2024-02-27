package com.colak.springreactiveredistutorial.hash.person.repository;

import com.colak.springreactiveredistutorial.hash.HashRepository;
import com.colak.springreactiveredistutorial.hash.person.model.Person;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonHashRepository extends HashRepository<Long, Person> {

    private static final String KEY = "person";

    public PersonHashRepository(ReactiveRedisTemplate<String, Person> personRedisTemplate) {
        super(KEY, personRedisTemplate.opsForHash());
    }
}
