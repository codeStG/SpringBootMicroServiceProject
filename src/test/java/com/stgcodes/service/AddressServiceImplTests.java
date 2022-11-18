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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.validation.AddressValidator;
import com.stgcodes.validation.enums.GeographicState;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTests {
	
	@Mock
	private AddressDao dao;
	
	@Mock
	private AddressValidator validator;
	
	@InjectMocks
	private AddressServiceImpl service;
	
	Address testAddress;
	
	@BeforeEach
	void setup() {
		testAddress = Address.builder()
				.addressId(0L)
				.lineOne("What St.")
				.city("Chicago")
				.state(GeographicState.IL)
				.zip("60626")
				.build();
	}
	
	@Test
	void testFindAll() {
		when(dao.findAll())
			.thenReturn(List.of(new AddressEntity()));
		
		service.findAll();
		
		verify(dao).findAll();
	}
	
	@Test
	void testFindById() {		
		when(dao.findById(1L))
			.thenReturn(new AddressEntity());
		
		service.findById(1L);
		
		verify(dao).findById(1L);
	}
	
	@Test
	void testSave() { 
		AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(testAddress);
		
		when(dao.save(addressEntity))
			.thenReturn(new AddressEntity());
		
		service.save(testAddress);
		
		verify(dao).save(addressEntity);
	}
	
	@Test
	void testUpdate() {
		testAddress.setAddressId(1L);
		
		AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(testAddress);
		
		when(dao.update(addressEntity))
			.thenReturn(new AddressEntity());
		
		service.update(testAddress, 1L);
	
		verify(dao).update(addressEntity);
	}
	
	@Test
	void testDelete() {
        when(dao.findById(1L))
        		.thenReturn(new AddressEntity());
        
        service.delete(1L);
		
		verify(dao).delete(dao.findById(1L));
	}
}
