package com.stgcodes.dao;

import java.util.List;

public interface GenericDao<T> {
    T findById(Long id);
    List<T> findAll();
    T save(T t);
    T update(T t);
    void delete(T t);
}
