package com.stgcodes.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
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
public class PhoneServiceImplTests {
	
	@Mock
	private PhoneDao dao;
	
	@Mock
	private PersonDao personDao;
	
	@Mock
	private PhoneValidator validator;
	
	@InjectMocks
	private PhoneServiceImpl service;
	
	Phone testPhone;
	
	@BeforeEach
	void setup() {
		testPhone = Phone.builder()
				.phoneNumber("214-214-2142")
				.phoneType(PhoneType.HOME)
				.build();
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
		when(dao.findById(1L))
			.thenReturn(new PhoneEntity());
		
		service.findById(1L);
		
		verify(dao).findById(1L);
	}
	
	@Test
	void testSave() { 
		Long personTestId = 1L;
		PersonEntity personEntity = new PersonEntity();
		personEntity.setPersonId(personTestId);
		
		PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
		personEntity.addPhone(phoneEntity);
		
		when(personDao.findById(personTestId))
			.thenReturn(personEntity);
		
		when(dao.save(phoneEntity))
			.thenReturn(phoneEntity);
		
		service.save(testPhone, personTestId);
		
		verify(dao).save(phoneEntity);
	}
	
//	@Test
//	void testUpdate() {
//		testPhone.setPhoneId(1L);
//		
//		PhoneEntity PhoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(testPhone);
//		
//		when(dao.update(PhoneEntity))
//			.thenReturn(new PhoneEntity());
//		
//		service.update(testPhone, 1L);
//	
//		verify(dao).update(PhoneEntity);
//	}
//	
//	@Test
//	void testDelete() {
//        when(dao.findById(1L))
//        		.thenReturn(new PhoneEntity());
//        
//        service.delete(1L);
//		
//		verify(dao).delete(dao.findById(1L));
//	}
}	