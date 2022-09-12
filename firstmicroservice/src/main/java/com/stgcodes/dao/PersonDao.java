package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PersonEntity> getPeople() {
        Session session = sessionFactory.openSession();

        return session.createQuery("FROM PersonEntity", PersonEntity.class).list();
    }

    @PostConstruct
    public void PostConstruct() {
        System.out.println("In Post Construct");
        this.getPeople().forEach(System.out::println);
        System.out.println("After for each");
    }
}
