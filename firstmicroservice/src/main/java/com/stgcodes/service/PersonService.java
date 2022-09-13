package com.stgcodes.service;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPeople();
    Person getPersonById(Long personId);
    Person addPerson(Person person);
    Person updatePerson(PersonEntity personEntity);
    Person deletePerson(Long personId);
}

