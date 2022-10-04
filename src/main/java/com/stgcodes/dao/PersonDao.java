package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PersonEntity> getPeople() {
        Query query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT * FROM PERSON_TBL")
                .addEntity(PersonEntity.class);

        return query.list();
    }

    public PersonEntity getPersonById(Long personId) {
        Query query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT * FROM PERSON_TBL WHERE person_id = " + personId)
                .addEntity(PersonEntity.class);

        return (PersonEntity) query.uniqueResult();
    }

    public PersonEntity addPerson(PersonEntity personEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(personEntity);

        session.getTransaction().commit();
        session.close();

        return personEntity;
    }

    public PersonEntity deletePerson(PersonEntity personEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(personEntity);
        session.getTransaction().commit();

        session.close();

        return personEntity;
    }

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
        this.addPerson(personEntity);
        this.getPeople().forEach(System.out::println);
        System.out.println(this.getPersonById(6L));
        this.deletePerson(personEntity);
        this.getPeople().forEach(System.out::println);
        System.out.println("After for each");
    }
}
