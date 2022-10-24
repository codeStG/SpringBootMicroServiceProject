package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortByFirstNameTests {

    Person personOne;
    Person personTwo;
    Person personThree;
    List<Person> people;

    @Before
    public void setUp() {
        personOne = Person.builder()
                .firstName("Lionel")
                .lastName("Montgomery")
                .username("limontgom")
                .dateOfBirth("08/05/2000")
                .socialSecurityNumber("222-69-3208")
                .gender("MALE")
                .email("limontgom@hotmail.com")
                .build();

        personTwo = Person.builder()
                .firstName("Zachary")
                .lastName("Still")
                .username("zastill")
                .dateOfBirth("04/25/1994")
                .socialSecurityNumber("365-89-5712")
                .gender("MALE")
                .email("zastill@gmail.com")
                .build();

        personThree = Person.builder()
                .firstName("Andreas")
                .lastName("Muniz")
                .username("anmuniz")
                .dateOfBirth("02/21/1978")
                .socialSecurityNumber("345-67-8912")
                .gender("MALE")
                .email("anmuniz@hotmail.com")
                .build();

        people = new ArrayList<>(Arrays.asList(personOne, personTwo, personThree));
    }

    @Test
    public void testPeopleSortedByFirstNameAscending() {
        List<Person> sortedPeople = new ArrayList<>(Arrays.asList(personThree, personOne, personTwo));

        people.sort(new SortByFirstName());

        Assert.assertArrayEquals(sortedPeople.toArray(), people.toArray());
    }
}