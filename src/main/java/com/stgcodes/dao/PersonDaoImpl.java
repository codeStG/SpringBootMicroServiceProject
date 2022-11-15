package com.stgcodes.dao;

import org.springframework.stereotype.Component;

import com.stgcodes.entity.PersonEntity;

@Component("personDao")
public class PersonDaoImpl extends DaoImpl<PersonEntity> implements PersonDao {
}
