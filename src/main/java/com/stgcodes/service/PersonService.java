package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    List<Person> findByCriteria(PersonCriteria criteria);
    Person findById(Long personId);
    Optional<Person> save(Person person);
    Person update(Person person, Long personId);
    void delete(Long addressId);
}

