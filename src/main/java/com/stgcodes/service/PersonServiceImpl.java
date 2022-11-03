package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exception.DataAccessException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.persistence.PersistenceException;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

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
    public Optional<List<Person>> findAll() {
        List<Person> people = new ArrayList<>();

        try {
            dao.findAll().forEach(e -> people.add(mapToModel(e)));
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return Optional.empty();
        }

        return Optional.of(people);
    }

    @Override
    public Optional<List<Person>> findByCriteria(PersonCriteria criteria) {
        List<Person> result = new ArrayList<>();

        try {
            dao.findAll(where(containsTextInFirstName(criteria.getFirstName()))
                    .and(containsTextInLastName(criteria.getLastName()))
                    .and(ofAge(criteria.getAge()))
                    .and(ofGender(criteria.getGender())))
                    .forEach(entity -> result.add(mapToModel(entity)));
        } catch (IllegalArgumentException e) {
            log.info("Invalid parameter provided - search yielded no results");
            return Optional.empty();
        }

        return Optional.of(result);
    }

    @Override
    public Optional<Person> findById(Long personId) {
        PersonEntity personEntity;

        try {
           personEntity = dao.findById(personId);
        } catch(Exception e) {
            log.info("No person found with provided ID");
            return Optional.empty();
        }

        if (personEntity == null) {
            return Optional.empty();
        }

        Person result = mapToModel(personEntity);

        return Optional.of(result);
    }

    @Override
    public Optional<Person> save(Person person) {
        if(isValidRequestBody(person)) {
            return savePerson(person);
        }

        return Optional.empty();
    }

    private Optional<Person> savePerson(Person person) {
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
        Person savedPerson;

        person.getPhones().forEach(phone -> phone.setPersonEntity(personEntity));

        try {
            result = dao.save(personEntity);
        }
        catch(PersistenceException e) {
            log.error("PersistenceException caught saving person - ", e);
            return Optional.empty();
        }

        savedPerson = mapToModel(result);
        return Optional.of(savedPerson);
    }

    @Override
    public Optional<Person> update(Person person, Long personId) {
        PersonEntity result;
        Optional<Person> personToUpdate = findById(personId);

        if(personToUpdate.isPresent()) {
            List<PhoneEntity> phones = personToUpdate.get().getPhones();
            PersonEntity personEntity = mapToEntity(person);
            personEntity.setPhones(phones);
            personEntity.setPersonId(personId);

            try {
                result = dao.update(personEntity);
            } catch(PersistenceException e) {
                log.error("PersistenceException caught updating person - ", e);
                return Optional.empty();
            }

            Person updatedPerson = mapToModel(result);
            return Optional.of(updatedPerson);
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long personId) {
        Optional<Person> person = findById(personId);
        if(person.isPresent()) {
            PersonEntity personEntity = mapToEntity(person.get());
            dao.delete(personEntity);
        }
    }

    private boolean isValidRequestBody(Person person) {
        BindingResult bindingResult = new BindException(person, "person");

        cleanPerson(person);
        validator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("person.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return false;
        }

        return true;
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
