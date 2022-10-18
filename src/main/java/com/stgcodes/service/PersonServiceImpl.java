package com.stgcodes.service;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Person;
import com.stgcodes.model.Phone;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            Phone phone = Phone.builder()
                    .phoneNumber("456-789-1234")
                    .phoneType("HOME")
                    .build();

            PersonEntity personEntity = mapToEntity(person);

            personEntity.getPhones().add(PhoneMapper.INSTANCE.phoneToPhoneEntity(phone));
            dao.save(personEntity);
            return mapToModel(personEntity);
        }

        throw new InvalidRequestBodyException();
    }

    @Override
    public Person update(Person person, Long personId) {
        findById(personId);

        PersonEntity personEntity = mapToEntity(person);
        personEntity.setPersonId(personId);
        dao.update(personEntity);

        return person;
    }

    @Override
    public void delete(Long personId) {
        Person person = findById(personId);
        dao.delete(mapToEntity(person));
    }

    public void cleanPerson(Person person) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        person.setFirstName(person.getFirstName().trim());
        person.setLastName(person.getLastName().trim());
        person.setUsername(person.getUsername().trim());
        person.setDateOfBirth(fieldFormatter.formatAsDate(person.getDateOfBirth()));
        person.setSocialSecurityNumber(fieldFormatter.separateBy(person.getSocialSecurityNumber(), "-"));
        person.setGender(fieldFormatter.formatAsEnum(person.getGender()));
        person.setEmail(person.getEmail().trim());
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

    public PersonEntity mapToEntity(Person person) {
        return PersonMapper.INSTANCE.personToPersonEntity(person);
    }

    public Person mapToModel(PersonEntity personEntity) {
        return PersonMapper.INSTANCE.personEntityToPerson(personEntity);
    }
}
