package com.stgcodes.samples;


import java.util.List;

import com.stgcodes.entity.PersonEntity;

public interface UserServiceDao {

    List<PersonEntity> getAllUser();

    PersonEntity findUserById(Long id);

    PersonEntity updatePerson(PersonEntity person);

    void addPerson(PersonEntity person);
}
