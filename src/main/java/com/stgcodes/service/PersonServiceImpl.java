package com.stgcodes.service;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.utils.FieldFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao dao;

    private final FieldFormatter fieldFormatter = new FieldFormatter();

    @Override
    public List<Person> getAllPeople() {
        List<PersonEntity> personEntities = dao.getPeople();
        List<Person> people = new ArrayList<>();

        for(PersonEntity personEntity : personEntities) {
            people.add(PersonMapper.INSTANCE.personEntityToPerson(personEntity));
        }

        return people;
    }

    @Override
    public Person getPersonById(Long personId) {
        PersonEntity personEntity = dao.getPersonById(personId);
        Person person;

        if(personEntity == null) {
            log.info("ID " + personId + " does not exist");
            throw new IdNotFoundException();
        } else {
            person = PersonMapper.INSTANCE.personEntityToPerson(personEntity);
        }

        return person;
    }

    @Override
    public Person addPerson(Person person) {
        Person cleansedPerson = Person.builder()
                .firstName(fieldFormatter.cleanWhitespace(person.getFirstName()))
                .lastName(fieldFormatter.cleanWhitespace(person.getLastName()))
                .username(fieldFormatter.cleanWhitespace(person.getUsername()))
                .dateOfBirth(fieldFormatter.separateBy(person.getDateOfBirth(), "/"))
                .socialSecurityNumber(fieldFormatter.separateBy(person.getSocialSecurityNumber(), "-"))
                .gender(fieldFormatter.formatAsEnum(person.getGender()))
                .email(fieldFormatter.cleanWhitespace(person.getEmail()))
                .build();

        PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(cleansedPerson);

        return PersonMapper.INSTANCE.personEntityToPerson(dao.addPerson(personEntity));
    }

    @Override
    public Person deletePerson(Long personId) {
        PersonEntity personEntity = dao.getPersonById(personId);

        return PersonMapper.INSTANCE.personEntityToPerson(dao.deletePerson(personEntity));
    }
}
