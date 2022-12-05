package com.stgcodes.specifications.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.validation.enums.Gender;

public class OfGenderSpecification implements Specification<UserEntity> {

	private static final long serialVersionUID = 6857167798115847457L;
	private final String gender;
	
	public OfGenderSpecification(String gender) {
		this.gender = gender;
	}
	
	@Override
	public Predicate toPredicate(Root<UserEntity> searchCriteria, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {		
		Expression<String> expression = searchCriteria.get("gender");

		if(StringUtils.isEmpty(gender)) {
			return allGenders(criteriaBuilder, expression);
		}
		
		try {
			return criteriaBuilder.equal(expression, Gender.valueOf(gender.toUpperCase()));
		} catch(IllegalArgumentException e) {
			Errors errors = new BindException(UserCriteria.builder().build(), "userCriteria");
			errors.rejectValue("gender", "gender.invalid");
			throw new InvalidRequestBodyException(UserCriteria.class, errors);
		}
	}

	private Predicate allGenders(CriteriaBuilder criteriaBuilder, Expression<String> expression) {
		return criteriaBuilder.or(
				criteriaBuilder.equal(expression, Gender.MALE),
				criteriaBuilder.equal(expression, Gender.FEMALE),
				criteriaBuilder.equal(expression, Gender.REFUSE)
				);
	}
}