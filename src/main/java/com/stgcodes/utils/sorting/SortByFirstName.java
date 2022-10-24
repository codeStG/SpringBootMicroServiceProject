package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;

public class SortByFirstName implements PersonSorter {

    @Override
    public int compare(Person personOne, Person personTwo) {
        return personOne.getFirstName().compareTo(personTwo.getFirstName());
    }
}
