package com.stgcodes.dao;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.entity.PersonEntity;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("personDao")
public class PersonDaoImpl extends DaoImpl<PersonEntity> implements PersonDao {
}
