package com.stgcodes.service;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
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
        List<PersonEntity> personEntity = dao.getPersonbyId(personId);
        Person person;

        if(personEntity.size() == 0) {
            log.info("ID " + personId + " does not exist");
            return null;
        } else {
            person = PersonMapper.INSTANCE.personEntityToPerson(personEntity.get(0));
            return person;
        }
    }

    @Override
    public Person addPerson(Person person) {
        PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(person);

        return PersonMapper.INSTANCE.personEntityToPerson(dao.addPerson(personEntity));
    }

    @Override
    public Person updatePerson(PersonEntity personEntity) {
        return null;
    }

    @Override
    public Person deletePerson(Long personId) {
        List<PersonEntity> personEntity = dao.getPersonbyId(personId);

        if(personEntity.size() == 0) {
            log.info("ID " + personId + " does not exist");
            throw new IdNotFoundException();
        }

        return PersonMapper.INSTANCE.personEntityToPerson(dao.deletePerson(personEntity.get(0)));
    }
}
