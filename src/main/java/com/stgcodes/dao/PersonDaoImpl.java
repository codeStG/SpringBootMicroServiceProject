package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("personDao")
public class PersonDaoImpl extends DaoImpl<PersonEntity> implements PersonDao {

}
