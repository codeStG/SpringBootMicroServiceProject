package com.stgcodes.service;

import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.validation.AddressValidator;
import com.stgcodes.validation.enums.GeographicState;
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
class AddressServiceImplTests {
	
	@Mock
	private AddressDao dao;
	
	@Mock
	private AddressValidator validator;
	
	@InjectMocks
	private AddressServiceImpl service;
	
	Address testAddress;
	Long id;
	
	@BeforeEach
	void setup() {
		testAddress = Address.builder()
				.addressId(0L)
				.lineOne("What St.")
				.city("Chicago")
				.state(GeographicState.IL)
				.zip("60626")
				.build();
		
		id = 1L;
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
		when(dao.findById(id))
			.thenReturn(new AddressEntity());
		
		service.findById(id);
		
		verify(dao).findById(id);
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
		testAddress.setAddressId(id);
		
		AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(testAddress);
		
		when(dao.update(addressEntity))
			.thenReturn(new AddressEntity());
		
		service.update(testAddress, id);
	
		verify(dao).update(addressEntity);
	}
	
	@Test
	void testDelete() {
        when(dao.findById(id))
        		.thenReturn(new AddressEntity());
        
        service.delete(id);
		
		verify(dao).delete(dao.findById(id));
	}
}
