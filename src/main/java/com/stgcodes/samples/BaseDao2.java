package com.stgcodes.samples;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stgcodes.exception.DataAccessException;

public abstract class BaseDao2<T> {

    @Autowired
    private SessionFactory factory;

    private Class<T> clazz;

    public void setClazz(Class< T > clazzToSet){
        this.clazz = clazzToSet;
    }

    protected Session getCurrentSession() {
        return factory.getCurrentSession();
    }


    public T findById(long id){
        try {
            return getCurrentSession().get(clazz, id);
        }
        catch(HibernateException he) {
           throwException(he);
           return null;
        }
    }

    public List findAll() {
        try {
            return getCurrentSession().createQuery("from " + clazz.getName()).list();
        }
        catch(HibernateException e) {
            throwException(e);
            return new ArrayList();
        }
    }

    public T create(T entity) {
        try {
            getCurrentSession().saveOrUpdate(entity);
            return entity;
        } catch(HibernateException e) {
            throwException(e);
            return null;
        }
    }

    public T update(T entity) {
        try {
            return (T) getCurrentSession().merge(entity);
        }
        catch (HibernateException e) {
            throwException(e);
            return null;
        }
    }

    public void delete(T entity) {
        try {
            getCurrentSession().delete(entity);
        }
        catch(HibernateException e) {
            throwException(e);
        }

    }

    private RuntimeException throwException (HibernateException e) {
        throw new DataAccessException(e);
    }

}
