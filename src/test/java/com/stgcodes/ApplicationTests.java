package com.stgcodes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stgcodes.endpoint.AddressController;
import com.stgcodes.endpoint.UserController;
import com.stgcodes.endpoint.PhoneController;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private AddressController addressController;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private PhoneController phoneController;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
		assertThat(addressController).isNotNull();
		assertThat(phoneController).isNotNull();

	}
}