package com.stgcodes.service;

import com.stgcodes.dao.GenericDao;
import com.stgcodes.exceptions.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public abstract class GenericServiceImpl<T> implements GenericService<T> {

    @Autowired
    GenericDao<T> dao;

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public T findById(Long id) {
        T t = dao.findById(id);

        if(t == null) {
            log.info("ID " + id + " does not exist");
            throw new IdNotFoundException();
        }

        return t;
    }

    @Override
    public T save(T t) {
        return dao.save(t);
    }

    @Override
    public void delete(Long id) {
        dao.delete(dao.findById(id));
    }
}
