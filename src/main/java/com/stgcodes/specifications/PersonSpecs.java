package com.stgcodes.specifications;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.validation.enums.Gender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSpecs {

    public static Specification<PersonEntity> containsTextInFirstName(String text) {

        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(text)) {
                    return criteriaBuilder.like(root.get("firstName"), "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + text.toLowerCase() + "%");
            }
        };
    }

    public static Specification<PersonEntity> containsTextInLastName(String text) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(text)) {
                    return criteriaBuilder.like(root.get("lastName"), "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + text.toLowerCase() + "%");
            }
        };
    }

    public static Specification<PersonEntity> ofAge(int age) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(Integer.toString(age))) {
                    return criteriaBuilder.like(root.get("age"), "%");
                }
                return criteriaBuilder.equal(root.get("age"), age);
            }
        };
    }

    public static Specification<PersonEntity> ofGender(String gender) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(gender)) {
                    Predicate male = criteriaBuilder.equal(root.get("gender"), Gender.MALE);
                    Predicate female = criteriaBuilder.equal(root.get("gender"), Gender.FEMALE);
                    Predicate refuse = criteriaBuilder.equal(root.get("gender"), Gender.REFUSE);

                    return criteriaBuilder.or(male, female, refuse);
                }
                return criteriaBuilder.equal(root.get("gender"), Gender.valueOf(gender.toUpperCase()));
            }
        };
    }
}