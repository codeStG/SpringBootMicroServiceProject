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

    public List<PersonEntity> findByCriteria(PersonCriteria personCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> criteria = criteriaBuilder.createQuery(PersonEntity.class);
        Root<PersonEntity> personRoot = criteria.from(PersonEntity.class);

        Predicate firstNameContains = criteriaBuilder.like(personRoot.get("firstName"), "%" + personCriteria.getFirstName() + "%");
        Predicate lastNameContains = criteriaBuilder.like(personRoot.get("lastName"), "%" + personCriteria.getLastName() + "%");
        Predicate ageIs = criteriaBuilder.equal(personRoot.get("age"), personCriteria.getAge());
        Predicate genderIs = criteriaBuilder.equal(personRoot.get("gender"), personCriteria.getGender().toUpperCase());

        criteria.select(personRoot)
                        .where(criteriaBuilder.and(firstNameContains, lastNameContains, ageIs, genderIs));

        TypedQuery<PersonEntity> query = entityManager.createQuery(criteria);
        return Optional.ofNullable(query.getResultList()).orElse(Collections.emptyList());
    }
}
