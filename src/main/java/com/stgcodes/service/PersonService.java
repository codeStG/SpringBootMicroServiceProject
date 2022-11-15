package com.stgcodes.service;

import java.util.List;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.model.Person;

public interface PersonService {
    List<Person> findAll();
    List<Person> findByCriteria(PersonCriteria criteria);
    Person findById(Long personId);
    Person save(Person person);
    Person update(Person person, Long personId);
    void delete(Long addressId);
}