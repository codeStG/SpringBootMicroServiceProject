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

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.dao.UserDao;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.UserMapper;
import com.stgcodes.model.User;
import com.stgcodes.specifications.user.NameLikeSpecification;
import com.stgcodes.specifications.user.UserSpecifications;
import com.stgcodes.validation.UserValidator;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
	
	@Mock
	private UserDao dao;
	
	@Mock
	private UserSpecifications specs;
	
	@Mock
	private UserValidator validator;
	
	@InjectMocks
	private UserServiceImpl service;
	
	User testUser;
	Long userId;
	
	@BeforeEach
	void setup() {
		PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
		testUser = User.builder()
				.userId(0L)
				.firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(List.of(testPhone))
                .build();
		
		userId = 1L;
	}
	
	@Test
	void testFindAll() {
		when(dao.findAll())
			.thenReturn(List.of(new UserEntity()));
		
		service.findAll();
		
		verify(dao).findAll();
	}
	
	@Test
	void testFindByCriteria() {
		UserCriteria searchCriteria = UserCriteria.builder()
				.firstName("r")
				.lastName("r")
				.age(0)
				.gender("")
				.build();
		
		Specification<UserEntity> spec = new NameLikeSpecification(searchCriteria.getFirstName());
		
		when(specs.whereMatches(searchCriteria))
			.thenReturn(spec);
				
		when(dao.findAll(spec))
			.thenReturn(List.of(new UserEntity()));
		
		service.findByCriteria(searchCriteria);
		
		verify(specs).whereMatches(searchCriteria);
		verify(dao).findAll(spec);
	}
	
	@Test
	void testFindById() {		
		when(dao.findById(userId))
			.thenReturn(new UserEntity());
		
		service.findById(userId);
		
		verify(dao).findById(userId);
	}
	
	@Test
	void testSave() { 		
		UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(testUser);
		
		when(dao.save(userEntity))
			.thenReturn(new UserEntity());
		
		service.save(testUser);
		
		verify(dao).save(userEntity);
	}
	
	@Test
	void testUpdate() {
		userId = 0L;
		UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(testUser);
		
		when(dao.findById(userId))
			.thenReturn(userEntity);
				
		when(dao.update(userEntity))
			.thenReturn(new UserEntity());
		
		service.update(testUser, userId);
		
		verify(dao).findById(userId);
		verify(dao).update(userEntity);
	}
	
	@Test
	void testDelete() {
		userId = 0L;
		UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(testUser);
		
		when(dao.findById(userId))
			.thenReturn(userEntity);
		
		service.delete(userId);
		
		verify(dao).delete(dao.findById(userId));
	}
}	