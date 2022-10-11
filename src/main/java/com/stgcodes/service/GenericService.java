package com.stgcodes.service;

import java.util.List;

public interface GenericService<T> {

    List<T> findAll();
    T findById(Long id);
    T save(T t);
    void delete(Long id);
}
