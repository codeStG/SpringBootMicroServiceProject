package com.stgcodes.specifications.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.stgcodes.entity.UserEntity;

public class NameLikeSpecification implements Specification<UserEntity> {

	private static final long serialVersionUID = 5048704077731414182L;
	private final String name;
	
	public NameLikeSpecification(String name) {
		this.name = name;
	}
	
	@Override
	public Predicate toPredicate(Root<UserEntity> searchCriteria, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Expression<String> expression = criteriaBuilder.lower(searchCriteria.get("firstName"));
		return criteriaBuilder.like(expression, "%" + name.toLowerCase() + "%");
	}

}