package com.stgcodes.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Transactional
public abstract class DaoImpl<T> implements Dao<T> {

    @PersistenceContext
    EntityManager entityManager;

    private Class<T> type;

    public DaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public T save(final T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }
}
