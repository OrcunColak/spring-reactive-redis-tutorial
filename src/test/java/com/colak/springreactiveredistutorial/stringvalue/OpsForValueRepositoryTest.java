package com.colak.springreactiveredistutorial.stringvalue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
// Testcontainers does not work for some reason
// @Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OpsForValueRepositoryTest {

    // @Container
    // @ServiceConnection
    // private static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:latest"));

    @Autowired
    private OpsForValueRepository<String, String> opsForValueRepository;


    @Test
    @Order(1)
    void testSet() {
        String key = "key";
        String value = "value";
        StepVerifier.create(opsForValueRepository.set(key, value))
                .expectNext(true) // Expect the result
                .verifyComplete(); // Verify that the operation is completed
    }

    @Test
    @Order(2)
    void testGet() {
        String key = "key";
        String expectedValue = "value";

        StepVerifier.create(opsForValueRepository.get(key))
                .expectNext(expectedValue) // Expect the result
                .verifyComplete(); // Verify that the operation is completed

    }


    @Test
    @Order(3)
    void testSetIfAbsent() {
        String key = "key";
        String value = "value";
        StepVerifier.create(opsForValueRepository.setIfAbsent(key, value))
                .expectNext(false) // Expect the result
                .verifyComplete(); // Verify that the operation is completed
    }
}
