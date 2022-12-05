package com.stgcodes.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.model.Person;
import com.stgcodes.specifications.person.NameLikeSpecification;
import com.stgcodes.specifications.person.PersonSpecifications;
import com.stgcodes.validation.PersonValidator;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTests {
	
	@Mock
	private PersonDao dao;
	
	@Mock
	private PersonSpecifications specs;
	
	@Mock
	private PersonValidator validator;
	
	@InjectMocks
	private PersonServiceImpl service;
	
	Person testPerson;
	Long personId;
	
	@BeforeEach
	void setup() {
		PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
		testPerson = Person.builder()
				.personId(0L)
				.firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(List.of(testPhone))
                .build();
		
		personId = 1L;
	}
	
	@Test
	void testFindAll() {
		when(dao.findAll())
			.thenReturn(List.of(new PersonEntity()));
		
		service.findAll();
		
		verify(dao).findAll();
	}
	
	@Test
	void testFindByCriteria() {
		PersonCriteria searchCriteria = PersonCriteria.builder()
				.firstName("r")
				.lastName("r")
				.age(0)
				.gender("")
				.build();
		
		Specification<PersonEntity> spec = new NameLikeSpecification(searchCriteria.getFirstName());
		
		when(specs.whereMatches(searchCriteria))
			.thenReturn(spec);
				
		when(dao.findAll(spec))
			.thenReturn(List.of(new PersonEntity()));
		
		service.findByCriteria(searchCriteria);
		
		verify(specs).whereMatches(searchCriteria);
		verify(dao).findAll(spec);
	}
	
	@Test
	void testFindById() {		
		when(dao.findById(personId))
			.thenReturn(new PersonEntity());
		
		service.findById(personId);
		
		verify(dao).findById(personId);
	}
	
	@Test
	void testSave() { 		
		PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(testPerson);
		
		when(dao.save(personEntity))
			.thenReturn(new PersonEntity());
		
		service.save(testPerson);
		
		verify(dao).save(personEntity);
	}
	
	@Test
	void testUpdate() {
		personId = 0L;
		PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(testPerson);
		
		when(dao.findById(personId))
			.thenReturn(personEntity);
				
		when(dao.update(personEntity))
			.thenReturn(new PersonEntity());
		
		service.update(testPerson, personId);
		
		verify(dao).findById(personId);
		verify(dao).update(personEntity);
	}
	
	@Test
	void testDelete() {
		personId = 0L;
		PersonEntity personEntity = PersonMapper.INSTANCE.personToPersonEntity(testPerson);
		
		when(dao.findById(personId))
			.thenReturn(personEntity);
		
		service.delete(personId);
		
		verify(dao).delete(dao.findById(personId));
	}
}	