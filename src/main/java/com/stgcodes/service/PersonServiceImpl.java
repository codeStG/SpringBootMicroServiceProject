package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.utils.sorting.PersonComparator;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stgcodes.specifications.PersonSpecs.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Component("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao dao;

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
    public List<Person> findByCriteria(PersonCriteria criteria) {
        List<Person> result = new ArrayList<>();
        dao.findAll(where(containsTextInFirstName(criteria.getFirstName()))
                .and(containsTextInLastName(criteria.getLastName()))
                .and(ofAge(criteria.getAge()))
                .and(ofGender(criteria.getGender())))
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
        isValidRequestBody(person);

        return savePerson(person);
    }

    private void isValidRequestBody(Person person) {
        BindingResult bindingResult = new BindException(person, "person");

        validator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            throw new InvalidRequestBodyException(Person.class, bindingResult);
        }

        person.setAge(calculateAge(person.getDateOfBirth()));
    }

    private Person savePerson(Person person) {
        /**
         * George - one school of thought on exception handling.
         *
         * My rule of thumb has been catch these types of cases at the service/domain level,
         * propagate domain specific exceptions from there and handle those exceptions in the controllers as needed,
         * perhaps by some specific error handler that displays the appropriate web page view
         * based on exception types, etc.
         *
         * In our case, we would return the appropriate HttpStatus code in the ResponseEntity in the controller
         *
         */
        PersonEntity personEntity = mapToEntity(person);
        person.getPhones().forEach(phone -> phone.setPersonEntity(personEntity));

        PersonEntity result = dao.save(personEntity);

        return mapToModel(result);
    }

    @Override
    public Person update(Person person, Long personId) {
        Person personToUpdate = findById(personId);
        List<PhoneEntity> phones = personToUpdate.getPhones();
        isValidRequestBody(person);

        PersonEntity personEntity = mapToEntity(person);
        personEntity.setPhones(phones);
        personEntity.setPersonId(personId);

        PersonEntity result = dao.update(personEntity);

        return mapToModel(result);
    }

    @Override
    public void delete(Long personId) {
        Person person = findById(personId);
        PersonEntity personEntity = mapToEntity(person);

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
