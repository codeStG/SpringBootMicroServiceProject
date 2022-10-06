package com.stgcodes.samples;


import com.stgcodes.entity.PersonEntity;

import java.util.List;

public interface UserServiceDao {

    List<PersonEntity> getAllUser();

    PersonEntity findUserById(Long id);

    PersonEntity updatePerson(PersonEntity person);

    void addPerson(PersonEntity person);
}
