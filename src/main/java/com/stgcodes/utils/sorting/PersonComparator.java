package com.stgcodes.utils.sorting;

import com.stgcodes.model.Person;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        int result = sortByLastName(p1, p2);

        if(result == 0) {
            result = sortByFirstName(p1, p2);

            if(result == 0) {
                result = sortByUsername(p1, p2);
            }
        }

        return result;
    }

    private int sortByFirstName(Person personOne, Person personTwo) {
        return personOne.getFirstName().compareTo(personTwo.getFirstName());
    }

    private int sortByLastName(Person personOne, Person personTwo) {
        return personOne.getLastName().compareTo(personTwo.getLastName());
    }

    private int sortByUsername(Person personOne, Person personTwo) {
        return personOne.getUsername().compareTo(personTwo.getUsername());
    }
}
