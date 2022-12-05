package com.stgcodes.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PhoneValidator;
import com.stgcodes.validation.enums.PhoneType;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTests {
	
	@Mock
	private PhoneDao dao;
	
	@Mock
	private PersonDao personDao;
	
	@Mock
	private PhoneValidator validator;
	
	@InjectMocks
	private PhoneServiceImpl service;
	
	Phone testPhone;
	Long phoneId;
	Long personId;
	
	@BeforeEach
	void setup() {
		testPhone = Phone.builder()
				.phoneId(0L)
				.phoneNumber("214-214-2142")
				.phoneType(PhoneType.HOME)
				.build();
		
		phoneId = 1L;
		personId = 1L;
	}
	
	@Test
	void testFindAll() {
		when(dao.findAll())
			.thenReturn(List.of(new PhoneEntity()));
		
		service.findAll();
		
		verify(dao).findAll();
	}
	
	@Test
	void testFindById() {		
		when(dao.findById(phoneId))
			.thenReturn(new PhoneEntity());
		
		service.findById(phoneId);
		
		verify(dao).findById(phoneId);
	}
	
	@Test
	void testSave() { 		
		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
		
		when(personDao.findById(personId))
			.thenReturn(new PersonEntity());
		
		when(dao.save(phoneEntity))
			.thenReturn(new PhoneEntity());
		
		service.save(testPhone, personId);
		
		verify(personDao).findById(personId);
		verify(dao).save(phoneEntity);
	}
	
	@Test
	void testUpdate() {
		testPhone.setPhoneId(phoneId);
		
		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
		
		when(dao.findById(phoneId))
			.thenReturn(new PhoneEntity());
		
		when(dao.update(phoneEntity))
			.thenReturn(new PhoneEntity());
		
		service.update(testPhone, phoneId);
	
		verify(dao).update(phoneEntity);
	}
	
	@Test
	void testDelete() {
		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
		phoneEntity.setPersonEntity(new PersonEntity());
		
        when(dao.findById(phoneId))
        		.thenReturn(phoneEntity);
        
        service.delete(phoneId);
		
		verify(dao).delete(dao.findById(phoneId));
	}
}	