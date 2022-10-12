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
    public Person cleanPerson(Person person) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        return Person.builder()
                .firstName(fieldFormatter.cleanWhitespace(person.getFirstName()))
                .lastName(fieldFormatter.cleanWhitespace(person.getLastName()))
                .username(fieldFormatter.cleanWhitespace(person.getUsername()))
                .dateOfBirth(fieldFormatter.separateBy(person.getDateOfBirth(), "/"))
                .socialSecurityNumber(fieldFormatter.separateBy(person.getSocialSecurityNumber(), "-"))
                .gender(fieldFormatter.formatAsEnum(person.getGender()))
                .email(fieldFormatter.cleanWhitespace(person.getEmail()))
                .build();
    }

    @Override
    public PersonEntity mapToEntity(Person person) {
        return PersonMapper.INSTANCE.personToPersonEntity(person);
    }

    public Person mapToModel(PersonEntity personEntity) {
        return PersonMapper.INSTANCE.personEntityToPerson(personEntity);
    }
}
