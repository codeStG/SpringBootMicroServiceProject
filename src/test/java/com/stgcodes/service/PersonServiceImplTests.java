package com.stgcodes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.stgcodes.dao.PersonDao;
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Person;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PersonValidator;
import com.stgcodes.validation.PhoneValidator;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTests {
	
	@Mock
	private PersonDao dao;
	
	@Mock
	private PhoneDao phoneDao;
	
	@Mock
	private PersonValidator validator;
	
	@InjectMocks
	private PersonServiceImpl service;
	
	Person testPerson;
//	Long phoneId;
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
		
//		phoneId = 1L;
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
	
//	@Test
//	void testUpdate() {
//		testPhone.setPhoneId(phoneId);
//		
//		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
//		
//		when(dao.findById(phoneId))
//			.thenReturn(new PhoneEntity());
//		
//		when(dao.update(phoneEntity))
//			.thenReturn(new PhoneEntity());
//		
//		service.update(testPhone, phoneId);
//	
//		verify(dao).update(phoneEntity);
//	}
//	
//	@Test
//	void testDelete() {
//		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
//		phoneEntity.setPersonEntity(new PersonEntity());
//		
//        when(dao.findById(phoneId))
//        		.thenReturn(phoneEntity);
//        
//        service.delete(phoneId);
//		
//		verify(dao).delete(dao.findById(phoneId));
//	}
}	