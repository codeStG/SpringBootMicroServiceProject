package com.stgcodes.service;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.utils.FieldFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("personService")
@Slf4j
public class PersonServiceImpl extends GenericServiceImpl<PersonEntity> implements PersonService {

    @Override
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

    @Override
    public PersonEntity mapToEntity(Person person) {
        return PersonMapper.INSTANCE.personToPersonEntity(person);
    }

    public Person mapToModel(PersonEntity personEntity) {
        return PersonMapper.INSTANCE.personEntityToPerson(personEntity);
    }
}
