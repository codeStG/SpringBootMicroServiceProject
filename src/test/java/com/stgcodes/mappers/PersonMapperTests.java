package com.stgcodes.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;

class PersonMapperTests {

    @Test
    void testMapModelToEntity() {
        Person person = Person.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(new ArrayList<>(List.of(new PhoneEntity())))
                .build();

        PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(person);

        Assertions.assertEquals(person.getFirstName(), personEntity.getFirstName());
        Assertions.assertEquals(person.getLastName(), personEntity.getLastName());
        Assertions.assertEquals(person.getUsername(), personEntity.getUsername());
        Assertions.assertEquals(person.getDateOfBirth(), personEntity.getDateOfBirth());
        Assertions.assertEquals(person.getSocialSecurityNumber(), personEntity.getSocialSecurityNumber());
        Assertions.assertEquals(person.getGender(), personEntity.getGender());
        Assertions.assertEquals(person.getEmail(), personEntity.getEmail());
        Assertions.assertEquals(person.getPhones(), personEntity.getPhones());
    }

    @Test
    void testMapEntityToModel() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName("Person");
        personEntity.setLastName("Name");
        personEntity.setUsername("pername");
        personEntity.setDateOfBirth(LocalDate.now());
        personEntity.setSocialSecurityNumber("123-45-6789");
        personEntity.setGender(Gender.REFUSE);
        personEntity.setEmail("email@address.com");


        Person person = PersonMapper.INSTANCE.personEntityToPerson(personEntity);

        Assertions.assertEquals(personEntity.getFirstName(), person.getFirstName());
        Assertions.assertEquals(personEntity.getLastName(), person.getLastName());
        Assertions.assertEquals(personEntity.getUsername(), person.getUsername());
        Assertions.assertEquals(personEntity.getDateOfBirth(), person.getDateOfBirth());
        Assertions.assertEquals(personEntity.getSocialSecurityNumber(), person.getSocialSecurityNumber());
        Assertions.assertEquals(personEntity.getGender(), person.getGender());
        Assertions.assertEquals(personEntity.getEmail(), person.getEmail());
    }

    @Test
    void testMapNullModelToNullEntity() {
        Person person = null;

        PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(person);

        Assertions.assertNull(personEntity);
    }

    @Test
    void testMapNullEntityToNullModel() {
        PersonEntity personEntity = null;

        Person person = PersonMapper.INSTANCE.personEntityToPerson(personEntity);

        Assertions.assertNull(person);
    }
}
