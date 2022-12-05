package com.stgcodes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stgcodes.endpoint.AddressController;
import com.stgcodes.endpoint.PersonController;
import com.stgcodes.endpoint.PhoneController;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private AddressController addressController;
	
	@Autowired
	private PersonController personController;
	
	@Autowired
	private PhoneController phoneController;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(personController).isNotNull();
		assertThat(addressController).isNotNull();
		assertThat(phoneController).isNotNull();

	}
}