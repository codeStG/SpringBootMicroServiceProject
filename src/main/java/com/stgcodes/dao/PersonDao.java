package com.stgcodes.dao;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.entity.PersonEntity;

import java.util.List;

public interface PersonDao extends Dao<PersonEntity> {
    List<PersonEntity> findByCriteria(PersonCriteria personCriteria);
}
