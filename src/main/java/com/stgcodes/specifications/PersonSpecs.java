package com.stgcodes.specifications;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.validation.enums.Gender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class PersonSpecs {

    private PersonSpecs() {
        throw new IllegalStateException("Specifications class not meant to be Instantiated");
    }

    public static Specification<PersonEntity> containsTextInFirstName(String text) {

        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(text)) {
                return criteriaBuilder.like(root.get("firstName"), "%");
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + text.toLowerCase() + "%");
        };
    }

    public static Specification<PersonEntity> containsTextInLastName(String text) {
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isEmpty(text)) {
                return criteriaBuilder.like(root.get("lastName"), "%");
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + text.toLowerCase() + "%");
        };
    }

    public static Specification<PersonEntity> ofAge(int age) {
        return (root, query, criteriaBuilder) -> {
            if (age == 0) {
                return criteriaBuilder.like(root.get("age").as(String.class), "%");
            }
            return criteriaBuilder.equal(root.get("age"), age);
        };
    }

    public static Specification<PersonEntity> ofGender(String gender) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(gender)) {
                Predicate male = criteriaBuilder.equal(root.get("gender"), Gender.MALE);
                Predicate female = criteriaBuilder.equal(root.get("gender"), Gender.FEMALE);
                Predicate refuse = criteriaBuilder.equal(root.get("gender"), Gender.REFUSE);

                return criteriaBuilder.or(male, female, refuse);
            }
            return criteriaBuilder.equal(root.get("gender"), Gender.valueOf(gender.toUpperCase()));
        };
    }
}