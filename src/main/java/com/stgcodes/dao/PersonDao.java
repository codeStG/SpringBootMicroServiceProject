package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
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

    public PersonEntity getPersonById(Long personId) {
        Query query = sessionFactory
                .openSession()
                .createSQLQuery("SELECT * FROM PERSON_TBL WHERE person_id = " + personId)
                .addEntity(PersonEntity.class);

        return (PersonEntity) query.uniqueResult();
    }

    public PersonEntity addPerson(PersonEntity personEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(personEntity);

        session.getTransaction().commit();
        session.close();

        return personEntity;
    }

    public PersonEntity deletePerson(PersonEntity personEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(personEntity);
        session.getTransaction().commit();

        session.close();

        return personEntity;
    }

    @PostConstruct
    public void PostConstruct() {
        PersonEntity personEntity = new PersonEntity("Bobbie", "Zamudio", "bozamudio", "09/12/2022", "123-45-6787", "female", "bozamudio@onenorth.com");

        System.out.println("In Post Construct");
        this.addPerson(personEntity);
        this.getPeople().forEach(System.out::println);
        System.out.println(this.getPersonById(6L));
        this.deletePerson(personEntity);
        this.getPeople().forEach(System.out::println);
        System.out.println("After for each");
    }
}