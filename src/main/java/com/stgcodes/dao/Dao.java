package com.stgcodes.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public interface Dao<T> {
    T findById(Long id);
    List<T> findAll();
    List<T> findAll(Specification<T> specs);
    T save(T t);
    T update(T t);
    void delete(T t);
}
