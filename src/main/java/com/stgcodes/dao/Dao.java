package com.stgcodes.dao;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface Dao<T> {
    T findById(Long id);
    List<T> findAll();
    List<T> findAll(Specification<T> specs);
    T save(T t);
    T update(T t);
    void delete(T t);
}
