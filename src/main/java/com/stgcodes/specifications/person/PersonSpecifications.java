package com.stgcodes.specifications.person;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.entity.PersonEntity;

@Component
public final class PersonSpecifications {

	private PersonSpecifications() {
		
	}
	
	public Specification<PersonEntity> whereMatches(PersonCriteria searchCriteria) {
		return where(hasNameLike(searchCriteria.getFirstName())
        		.and(where(hasNameLike(searchCriteria.getLastName()))))
        		.and(where(ofAge(searchCriteria.getAge())))
        		.and(where(ofGender(searchCriteria.getGender())));
	}
	
	private Specification<PersonEntity> hasNameLike(String name) {
		return new NameLikeSpecification(name);
	}
	
	private Specification<PersonEntity> ofAge(int age) {
		return new OfAgeSpecification(age);
	}
	
	private Specification<PersonEntity> ofGender(String gender) {
		return new OfGenderSpecification(gender);
	}
}