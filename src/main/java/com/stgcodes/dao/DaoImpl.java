package com.stgcodes.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class DaoImpl<T> implements Dao<T> {

    @Autowired
    SessionFactory sessionFactory;

    private Class<T> type;

    public DaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T data = session.find(type, id);

        session.getTransaction().commit();

        return data;
    }

    @Override
    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);

        List<T> data = session.createQuery(criteria).getResultList();

        session.getTransaction().commit();

        return data;
    }

    @Override
    public T save(final T t) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(t);
        session.getTransaction().commit();

        return t;
    }

    @Override
    public void delete(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(t);
        session.getTransaction().commit();
    }
}
