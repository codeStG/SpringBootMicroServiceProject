package com.stgcodes.service;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        List<PersonEntity> found = dao.findAll(where(containsTextInFirstName(criteria.getFirstName()))
                .and(containsTextInLastName(criteria.getLastName()))
                .and(ofAge(criteria.getAge()))
                .and(ofGender(criteria.getGender())));

        List<Person> people = new ArrayList<>();
        found.forEach(p -> people.add(mapToModel(p)));

        return people;
    }

    @Override
    public Person findById(Long personId) {
        PersonEntity personEntity = dao.findById(personId);

        if(personEntity == null) {
            log.info("ID " + personId + " does not exist");
            throw new IdNotFoundException();
        }

        return mapToModel(personEntity);
    }

    @Override
    public Person save(Person person) {
        if(isValidRequestBody(person)) {
            PersonEntity personEntity = mapToEntity(person);
            person.getPhones().forEach(p -> p.setPersonEntity(personEntity));

            return mapToModel(dao.save(personEntity));
        }

        throw new InvalidRequestBodyException();
    }

    @Override
    public Person update(Person person, Long personId) {
        List<PhoneEntity> phones = findById(personId).getPhones();

        PersonEntity personEntity = mapToEntity(person);
        personEntity.setPhones(phones);
        personEntity.setPersonId(personId);

        return mapToModel(dao.update(personEntity));
    }

    @Override
    public void delete(Long personId) {
        Person person = findById(personId);
        dao.delete(mapToEntity(person));
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
        FieldFormatter fieldFormatter = new FieldFormatter();

        /**
         * TODO: apache common StringUtils class provides a null safe way to handle trimming.
         * I recommend the String manipulation be done using the Apache Commons class
         * (the dependency is already defined in the pom file)
         */

        person.setFirstName(person.getFirstName().trim());
        person.setLastName(person.getLastName().trim());
        person.setUsername(person.getUsername().trim());
        person.setAge(calculateAge(person.getDateOfBirth()));
        person.setSocialSecurityNumber(fieldFormatter.separateBy(person.getSocialSecurityNumber(), "-"));
        person.setEmail(person.getEmail().trim());
    }

    private int calculateAge(String dateOfBirth) {
        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public PersonEntity mapToEntity(Person person) {
        return PersonMapper.INSTANCE.personToPersonEntity(person);
    }

    public Person mapToModel(PersonEntity personEntity) {
        return PersonMapper.INSTANCE.personEntityToPerson(personEntity);
    }
}
