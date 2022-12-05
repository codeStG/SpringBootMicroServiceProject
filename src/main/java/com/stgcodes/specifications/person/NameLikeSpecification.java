package com.stgcodes.specifications.person;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.stgcodes.entity.PersonEntity;

public class NameLikeSpecification implements Specification<PersonEntity> {

	private static final long serialVersionUID = 5048704077731414182L;
	private final String name;
	
	public NameLikeSpecification(String name) {
		this.name = name;
	}
	
	@Override
	public Predicate toPredicate(Root<PersonEntity> searchCriteria, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Expression<String> expression = criteriaBuilder.lower(searchCriteria.get("firstName"));
		return criteriaBuilder.like(expression, "%" + name.toLowerCase() + "%");
	}

}