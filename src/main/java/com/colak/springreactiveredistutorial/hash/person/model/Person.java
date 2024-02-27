package com.colak.springreactiveredistutorial.hash.person.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // This is required for Jackson deserialization
@AllArgsConstructor
@EqualsAndHashCode
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
