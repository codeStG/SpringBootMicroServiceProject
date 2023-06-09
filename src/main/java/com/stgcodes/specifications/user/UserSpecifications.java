package com.stgcodes.specifications.user;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserSpecifications {

	private UserSpecifications() {
		
	}
	
	public Specification<UserEntity> whereMatches(UserCriteria searchCriteria) {
		return where(hasNameLike(searchCriteria.getFirstName())
        		.and(where(hasNameLike(searchCriteria.getLastName()))))
        		.and(where(ofAge(searchCriteria.getAge())))
        		.and(where(ofGender(searchCriteria.getGender())));
	}
	
	private Specification<UserEntity> hasNameLike(String name) {
		return new NameLikeSpecification(name);
	}
	
	private Specification<UserEntity> ofAge(int age) {
		return new OfAgeSpecification(age);
	}
	
	private Specification<UserEntity> ofGender(String gender) {
		return new OfGenderSpecification(gender);
	}
}