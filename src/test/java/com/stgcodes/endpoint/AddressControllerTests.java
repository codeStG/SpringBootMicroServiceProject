package com.stgcodes.endpoint;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AddressControllerTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static Address testAddress;
	
	@BeforeAll
	static void setup() {
		testAddress = Address.builder()
				.addressId(1L)
				.lineOne("1234 Davis St.")
				.lineTwo("Apt. 1")
				.city("Grand Prairie")
				.state(GeographicState.TX)
				.zip("75052")
				.build();
	}
	
	@Test
	void getAllShouldReturnListOfAddresses() {
		ResponseEntity<List<Address>> responseEntity = restTemplate.exchange("http://localhost:" + port + "/addresses/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {});

		List<Address> addresses = responseEntity.getBody();
		
		Assertions.assertEquals(testAddress, addresses.get(0));
	}
	
	@Test
	void getByIdShouldReturnAddress() {
		ResponseEntity<Address> responseEntity = restTemplate.exchange("http://localhost:" + port + "/addresses/id?addressId=1", HttpMethod.GET, null, new ParameterizedTypeReference<Address>() {});

		Address address = responseEntity.getBody();
		
		Assertions.assertEquals(testAddress, address);
	}
	
	@Test
	void addAddressShouldReturnCreatedAddress() {
		Address newAddress = Address.builder()
				.lineOne("Random Rd.")
				.lineTwo("Unit 42")
				.city("Seattle")
				.state(GeographicState.WA)
				.zip("98101")
				.build();
		
		HttpEntity<Address> requestEntity = new HttpEntity<>(newAddress);
		
		ResponseEntity<Address> responseEntity = restTemplate.exchange("http://localhost:" + port + "/addresses/add", HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Address>() {});

		Address address = responseEntity.getBody();

		/** TODO: possibly implement searchability and then change this to use the AddressService
		 *  to search for the newly created Address (the service.searchBy() call should be
		 *  the expected argument)
		**/
		newAddress.setAddressId(address.getAddressId());
		
		Assertions.assertEquals(newAddress, address);
	}
	
	@Test
	void updateAddressShouldReturnUpdatedAddress() {
		testAddress.setLineOne("Different Rd.");
		testAddress.setLineTwo("No. 24");
		
		HttpEntity<Address> requestEntity = new HttpEntity<>(testAddress);
		ResponseEntity<Address> responseEntity = restTemplate.exchange("http://localhost:" + port + "/addresses/update?addressId=1", HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Address>() {});
	
		Address address = responseEntity.getBody();
		
		Assertions.assertEquals(testAddress, address);
	}
	
	@Test
	void deleteAddressShouldRemoveAddressFromDatabase() {
		restTemplate.exchange("http://localhost:" + port + "/addresses/remove?addressId=1", HttpMethod.DELETE, null, new ParameterizedTypeReference<Address>() {});
		ResponseEntity<Address> responseEntity = restTemplate.exchange("http://localhost:" + port + "/addresses/id?addressId=1", HttpMethod.GET, null, new ParameterizedTypeReference<Address>() {});
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
