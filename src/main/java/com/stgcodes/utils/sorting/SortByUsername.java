package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;

public class SortByUsername implements PersonSorter {

    @Override
    public int compare(Person personOne, Person personTwo) {
        return personOne.getUsername().compareTo(personTwo.getUsername());
    }
}