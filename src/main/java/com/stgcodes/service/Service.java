package com.stgcodes.service;

import java.util.List;

public interface Service<T> {

    List<T> findAll();
    T findById(Long id);
    T save(T t);
    T update(T t, Long id);
    void delete(Long id);
}
