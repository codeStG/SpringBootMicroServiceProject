package com.stgcodes.service;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.model.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService extends GenericService<PersonEntity> {
    void cleanPerson(Person person);
    PersonEntity mapToEntity(Person person);
    Person mapToModel(PersonEntity personEntity);
}

