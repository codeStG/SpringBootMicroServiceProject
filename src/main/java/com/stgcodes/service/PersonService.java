package com.stgcodes.service;

import com.stgcodes.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    List<Person> getAllPeople();
    Person getPersonById(Long personId);
    Person addPerson(Person person);
    void deletePerson(Long personId);
}

