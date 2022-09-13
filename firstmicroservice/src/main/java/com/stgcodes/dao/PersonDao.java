package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
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
                .openSession()
                .createSQLQuery("SELECT * FROM PERSON_TBL")
                .addEntity(PersonEntity.class);

        return query.list();
    }

    public List<PersonEntity> getPersonbyId(Long personId) {
        Query query = sessionFactory
                .openSession()
                .createSQLQuery("SELECT * FROM PERSON_TBL WHERE person_id = " + personId)
                .addEntity(PersonEntity.class);

        return query.list();
    }

    public PersonEntity addPerson(PersonEntity person) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(person);

        session.getTransaction().commit();
        session.close();

        return person;
    }

    public PersonEntity updatePersonById(Long personId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        PersonEntity personEntity = session.load(PersonEntity.class, personId);

        personEntity.setFirstName("Roberta");
        session.update(personEntity);
        session.getTransaction().commit();

        personEntity = session.load(PersonEntity.class, personId);

        session.close();

        return personEntity;
    }

    public void deletePersonById(Long personId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        PersonEntity personEntity = session.load(PersonEntity.class, personId);

        session.delete(personEntity);
        session.getTransaction().commit();

        session.close();
    }

    @PostConstruct
    public void PostConstruct() {
        System.out.println("In Post Construct");
        this.addPerson(new PersonEntity("Bobbie", "Zamudio", "bozamudio", "09/12/2022", "123-45-6787", "female", "bozamudio@onenorth.com"));
        this.getPeople().forEach(System.out::println);
        this.updatePersonById(6L);
        this.getPersonbyId(6L).forEach(System.out::println);
        this.deletePersonById(6L);
        this.getPeople().forEach(System.out::println);
        System.out.println("After for each");
    }
}
