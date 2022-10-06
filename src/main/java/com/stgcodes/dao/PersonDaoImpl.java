package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("personDao")
public class PersonDaoImpl extends DaoImpl<PersonEntity> implements PersonDao {

    @PostConstruct
    public void PostConstruct() {
        Person person = Person.builder()
                .firstName("Bobbie")
                .lastName("Zamudio")
                .username("bozamudio")
                .dateOfBirth("09/12/2022")
                .socialSecurityNumber("123-45-6787")
                .gender("female")
                .email("bozamudio@onenorth.com")
                .build();

        PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(person);

        System.out.println("In Post Construct");
        this.save(personEntity);
        this.findAll().forEach(System.out::println);
        System.out.println(this.findById(6L));
        this.delete(personEntity);
        this.findAll().forEach(System.out::println);
        System.out.println("After for each");
    }
}
