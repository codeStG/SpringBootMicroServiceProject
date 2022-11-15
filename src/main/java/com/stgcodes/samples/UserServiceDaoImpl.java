package com.stgcodes.samples;


import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.stgcodes.entity.PersonEntity;

@Repository
public class UserServiceDaoImpl extends BaseDao2<PersonEntity> implements UserServiceDao {

    @PostConstruct
    public void onPostConstruct() {
        setClazz(PersonEntity.class);
    }

    @Override
    public List<PersonEntity> getAllUser() {

        return findAll();
    }

    @Override
    public PersonEntity findUserById(Long id) {

        return findById(id);
    }

    @Override
    public PersonEntity updatePerson(PersonEntity entity) {
        return update(entity);
    }

    @Override
    public void addPerson(PersonEntity person) {
        create(person);
    }
}
