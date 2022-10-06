package com.stgcodes.samples;


import com.stgcodes.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

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
