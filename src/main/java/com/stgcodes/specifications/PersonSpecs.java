package com.stgcodes.specifications;

import com.stgcodes.entity.PersonEntity;
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
                if(StringUtils.isBlank(text)) {
                    return criteriaBuilder.like(root.get("firstName"), "%");
                }
                return criteriaBuilder.like(root.get("firstName"), "%" + text + "%");
            }
        };
    }

    public static Specification<PersonEntity> containsTextInLastName(String text) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isBlank(text)) {
                    return criteriaBuilder.like(root.get("lastName"), "%");
                }
                return criteriaBuilder.like(root.get("lastName"), "%" + text + "%");
            }
        };
    }

    public static Specification<PersonEntity> ofAge(int age) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isBlank(Integer.toString(age))) {
                    return criteriaBuilder.like(root.get("age"), "%");
                }
                return criteriaBuilder.equal(root.get("age"), age);
            }
        };
    }

    public static Specification<PersonEntity> ofGender(String text) {
        return new Specification<PersonEntity>() {
            @Override
            public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isBlank(text)) {
                    return criteriaBuilder.like(root.get("gender"), "%");
                }
                return criteriaBuilder.equal(root.get("gender"), text);
            }
        };
    }
}
