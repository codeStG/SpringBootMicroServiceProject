package com.stgcodes.specifications.person;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.stgcodes.entity.PersonEntity;

public class OfAgeSpecification implements Specification<PersonEntity> {

	private static final long serialVersionUID = -272159376847232066L;
	private final int age;
	
	public OfAgeSpecification(int age) {
		this.age = age;
	}
	
	@Override
	public Predicate toPredicate(Root<PersonEntity> searchCriteria, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Expression<String> expression = searchCriteria.get("age").as(String.class);

		if(age < 1) {
			return criteriaBuilder.like(expression, "%");
		}
		
		return criteriaBuilder.equal(expression, String.valueOf(age));
	}
}