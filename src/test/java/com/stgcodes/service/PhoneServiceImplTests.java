package com.stgcodes.service;

import com.stgcodes.dao.PhoneDao;
import com.stgcodes.dao.UserDao;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PhoneValidator;
import com.stgcodes.validation.enums.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTests {
	
	@Mock
	private PhoneDao dao;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PhoneValidator validator;
	
	@InjectMocks
	private PhoneServiceImpl service;
	
	Phone testPhone;
	Long phoneId;
	Long userId;
	
	@BeforeEach
	void setup() {
		testPhone = Phone.builder()
				.phoneId(0L)
				.phoneNumber("214-214-2142")
				.phoneType(PhoneType.HOME)
				.build();
		
		phoneId = 1L;
		userId = 1L;
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
		
		when(userDao.findById(userId))
			.thenReturn(new UserEntity());
		
		when(dao.save(phoneEntity))
			.thenReturn(new PhoneEntity());
		
		service.save(testPhone, userId);
		
		verify(userDao).findById(userId);
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
		phoneEntity.setUserEntity(new UserEntity());
		
        when(dao.findById(phoneId))
        		.thenReturn(phoneEntity);
        
        service.delete(phoneId);
		
		verify(dao).delete(dao.findById(phoneId));
	}
}	