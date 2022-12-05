package com.stgcodes.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.specifications.person.PersonSpecifications;
import com.stgcodes.utils.sorting.PersonComparator;
import com.stgcodes.validation.PersonValidator;

@Component("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao dao;
    
    @Autowired
    private PersonSpecifications specs;

    @Autowired
    private PersonValidator validator;

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        dao.findAll().forEach(e -> people.add(mapToModel(e)));
        people.sort(new PersonComparator());

        return people;
    }

    @Override
    public List<Person> findByCriteria(PersonCriteria searchCriteria) {
        List<Person> result = new ArrayList<>();
                
        dao.findAll(specs.whereMatches(searchCriteria))
                .forEach(entity -> result.add(mapToModel(entity)));

        result.sort(new PersonComparator());

        return result;
    }

    @Override
    public Person findById(Long personId) {
        PersonEntity personEntity;
        personEntity = dao.findById(personId);

        return mapToModel(personEntity);
    }

    @Override
    public Person save(Person person) {
        validator.validate(person);
        
        PersonEntity personEntity = mapToEntity(person);
        person.getPhones().forEach(phone -> phone.setPersonEntity(personEntity));
        person.setAge(calculateAge(person.getDateOfBirth()));
        
        PersonEntity result = dao.save(personEntity);
        
        return mapToModel(result);
    }

    @Override
    public Person update(Person person, Long personId) {
        PersonEntity existingPerson = dao.findById(personId);
        List<PhoneEntity> phones = existingPerson.getPhones();
        validator.validate(person);

        PersonEntity personEntity = mapToEntity(person);
        personEntity.setPhones(phones);
        personEntity.setPersonId(personId);

        PersonEntity result = dao.update(personEntity);

        return mapToModel(result);
    }

    @Override
    public void delete(Long personId) {
        PersonEntity personEntity = dao.findById(personId);

        dao.delete(personEntity);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private PersonEntity mapToEntity(Person person) {
        return PersonMapper.INSTANCE.personToPersonEntity(person);
    }

    private Person mapToModel(PersonEntity personEntity) {
        return PersonMapper.INSTANCE.personEntityToPerson(personEntity);
    }
}
