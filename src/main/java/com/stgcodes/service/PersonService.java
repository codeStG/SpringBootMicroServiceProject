package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.exception.DataAccessException;
import com.stgcodes.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<List<Person>> findAll();
    Optional<List<Person>> findByCriteria(PersonCriteria criteria);
    Optional<Person> findById(Long personId);
    Optional<Person> save(Person person);
    Optional<Person> update(Person person, Long personId);
    void delete(Long addressId);
}