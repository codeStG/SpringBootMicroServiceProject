package com.stgcodes.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public abstract class Dao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    abstract Optional<T> findById(Long id);
    abstract List<T> findAll();

    public void save(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(t);
        session.getTransaction().commit();
    }

    public void delete(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(t);
        session.getTransaction().commit();
    }
}