package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;

import java.util.Comparator;

public class SortByUsername implements Comparator<Person> {

    @Override
    public int compare(Person personOne, Person personTwo) {
        return personOne.getUsername().compareTo(personTwo.getUsername());
    }
}