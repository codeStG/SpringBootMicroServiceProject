package com.stgcodes.dao;

import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
public abstract class DaoImpl<T> implements Dao<T> {

    @PersistenceContext
    EntityManager entityManager;

    private final Class<T> type;

    protected DaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T findById(Long id) {
        T t = entityManager.find(type, id);

        if(t == null) {
            log.warn("The ID provided does not exist - " + id);
            throw new IdNotFoundException(type, id.toString());
        }

        return t;
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);

        List<T> result = entityManager.createQuery(criteria).getResultList();
        return result.isEmpty() ? Collections.emptyList() : result;
    }

    public List<T> findAll(Specification<T> specs) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> root = criteria.from(type);

        Predicate predicate = specs.toPredicate(root, criteria, builder);

        criteria.select(root).where(predicate);

        List<T> result = entityManager.createQuery(criteria).getResultList();
        return result.isEmpty() ? Collections.emptyList() : result;
    }

    @Override
    public T save(final T t) {
        try {
            entityManager.persist(t);
        } catch(PersistenceException ex) {
            log.error(ex.getMessage());
            throw new DataAccessException("Encountered an error while attempting to save");
        }
        return t;
    }

    @Override
    public T update(T t) {
        try {
            entityManager.merge(t);
        } catch(PersistenceException ex) {
            log.error(ex.getMessage());
            throw new DataAccessException("Encountered an error while attempting to update");
        }

        return t;
    }

    @Override
    public void delete(T t) {
        entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
    }
}