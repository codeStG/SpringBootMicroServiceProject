package com.stgcodes.dao;

import com.stgcodes.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component("personDao")
public class PersonDaoImpl extends GenericDaoImpl<PersonEntity> implements PersonDao {

}
