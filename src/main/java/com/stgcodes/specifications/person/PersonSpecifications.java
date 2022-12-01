package com.stgcodes.specifications.person;

import org.springframework.data.jpa.domain.Specification;

import com.stgcodes.entity.PersonEntity;

public final class PersonSpecifications {

	private PersonSpecifications() {
		
	}
	
	public static Specification<PersonEntity> hasNameLike(String name) {
		return new NameLikeSpecification(name);
	}
	
	public static Specification<PersonEntity> ofAge(int age) {
		return new OfAgeSpecification(age);
	}
	
	public static Specification<PersonEntity> ofGender(String gender) {
		return new OfGenderSpecification(gender);
	}
}