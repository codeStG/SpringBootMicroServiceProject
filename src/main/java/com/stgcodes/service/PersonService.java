package com.stgcodes.service;

import com.stgcodes.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPeople();
    Person getPersonById(Long personId);
    Person addPerson(Person person);
    Person deletePerson(Long personId);
}

