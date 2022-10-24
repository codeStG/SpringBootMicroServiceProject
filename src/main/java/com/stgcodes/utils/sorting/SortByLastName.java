package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;

public class SortByLastName implements PersonSorter {

    @Override
    public int compare(Person personOne, Person personTwo) {
        return personOne.getLastName().compareTo(personTwo.getLastName());
    }
}
