package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        return result;
    }

    @Override
    public Person findById(Long personId) {
        PersonEntity personEntity;

        try {
           personEntity = dao.findById(personId);
        } catch(IdNotFoundException e) {
            log.error("No person exists with ID: " + personId);
            throw new IdNotFoundException("No person exists with ID: " + personId);
        }

        return mapToModel(personEntity);
    }

    @Override
    public Person save(Person person) {
        Person savedPerson;

        try {
            isValidRequestBody(person);
        } catch(IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new InvalidRequestBodyException(e.getMessage());
        }

        try {
            savedPerson = savePerson(person);
        } catch(PersistenceException e) {
            log.error(e.getMessage());
            throw new DataAccessException("There was an internal error while trying to save the Person");
        }

        return savedPerson;
    }

    private void isValidRequestBody(Person person) {
        BindingResult bindingResult = new BindException(person, "person");

        cleanPerson(person);
        validator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");
            StringBuilder errorMessage = new StringBuilder();

            errorMessage.append(messageSource.getMessage("person.invalid", new Object[]{bindingResult.getErrorCount()}, Locale.US));
            bindingResult.getAllErrors().forEach(error -> errorMessage.append("\n").append(messageSource.getMessage(error, Locale.US)));

            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    private void cleanPerson(Person person) {
        /**
         * TODO: apache common StringUtils class provides a null safe way to handle trimming.
         * I recommend the String manipulation be done using the Apache Commons class
         * (the dependency is already defined in the pom file)
         */

        person.setFirstName(StringUtils.trim(person.getFirstName()));
        person.setLastName(StringUtils.trim(person.getLastName()));
        person.setUsername(StringUtils.trim(person.getUsername()));
        person.setAge(calculateAge(person.getDateOfBirth()));
        person.setSocialSecurityNumber(StringUtils.trim(person.getSocialSecurityNumber()));
        person.setEmail(StringUtils.trim(person.getEmail()));
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
        PersonEntity result;

        person.getPhones().forEach(phone -> phone.setPersonEntity(personEntity));

        try {
            result = dao.save(personEntity);
        } catch(PersistenceException e) {
            log.error(e.getLocalizedMessage());
            throw new PersistenceException("PersistenceException caught saving person - translating into DataAccessException");
        }

        return mapToModel(result);
    }

    @Override
    public Person update(Person person, Long personId) {
        Person personToUpdate = findById(personId);
        PersonEntity result;

        List<PhoneEntity> phones = personToUpdate.getPhones();
        PersonEntity personEntity = mapToEntity(person);
        personEntity.setPhones(phones);
        personEntity.setPersonId(personId);

        try {
            result = dao.update(personEntity);
        } catch(PersistenceException e) {
            log.error(e.getLocalizedMessage());
            throw new PersistenceException("PersistenceException caught updating person - translating into DataAccessException");
        }

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
