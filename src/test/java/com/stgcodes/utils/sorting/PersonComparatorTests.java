package com.stgcodes.utils.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stgcodes.model.Person;

class PersonComparatorTests {

    List<Person> people;

    @BeforeEach
    public void setUp() {
        people = new ArrayList<>();

        people.add(Person.builder()
                .firstName("Lionel")
                .lastName("Montgomery")
                .username("limontgom")
                .email("limontgom@hotmail.com")
                .build());

        people.add(Person.builder()
                .firstName("Zachary")
                .lastName("Still")
                .username("zastill")
                .email("zastill@gmail.com")
                .build());

        people.add(Person.builder()
                .firstName("Andreas")
                .lastName("Muniz")
                .username("anmuniz")
                .email("anmuniz@hotmail.com")
                .build());

        people.add(Person.builder()
                .firstName("Lionel")
                .lastName("Montgomery")
                .username("liomontgom")
                .email("liomontgom@hotmail.com")
                .build());
    }

    @Test
    void testPeopleSortedByFirstNameAscending() {
        Collections.shuffle(people);
        people.sort(new PersonComparator());

        for(int i = 0; i < people.size() - 1; i++) {
            Person p1 = people.get(i);
            Person p2 = people.get(i + 1);

            int result = p1.getLastName().compareTo(p2.getLastName());

            if(result == 0) {
                result = p1.getFirstName().compareTo(p2.getFirstName());

                if(result == 0) {
                    result = p1.getUsername().compareTo(p2.getUsername());
                }
            }

            Assertions.assertTrue(result < 0);
        }
    }
}