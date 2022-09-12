package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PersonEntity> getPeople() {
        Query query = sessionFactory.openSession().createSQLQuery("SELECT * FROM PERSON_TBL").addEntity(PersonEntity.class);

        return query.list();
    }

    @PostConstruct
    public void PostConstruct() {
        System.out.println("In Post Construct");
        this.getPeople().forEach(System.out::println);
        System.out.println("After for each");
    }
}
