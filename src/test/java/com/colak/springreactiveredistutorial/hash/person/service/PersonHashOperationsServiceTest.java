package com.colak.springreactiveredistutorial.hash.person.service;

import com.colak.springreactiveredistutorial.hash.person.model.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonHashOperationsServiceTest {

    @Autowired
    private PersonHashOperationsService personHashOperationsService;

    private final LocalDate birthDate = LocalDate.of(1976, 2, 6);
    private final Person person = new Person(1L, "John", "Doe", birthDate);

    @Test
    @Order(1)
    void testPut() {
        personHashOperationsService.remove(person.getId()).block();

        StepVerifier.create(personHashOperationsService.put(person))
                .expectNext(true) // Expect the result
                .verifyComplete(); // Verify that the operation is completed
    }

    @Test
    @Order(2)
    void testGet() {
        StepVerifier.create(personHashOperationsService.get(person.getId()))
                .expectNext(person) // Expect the result
                .verifyComplete(); // Verify that the operation is completed
    }
}
