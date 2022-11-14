package com.stgcodes.endpoint;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;

@SpringBootTest()
@AutoConfigureMockMvc
class AddressControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AddressDao addressDao;
	
	private Address testAddress;
	private ResourceBundleMessageSource messageSource;
	
	@BeforeEach
	void setup() {
		testAddress = Address.builder()
				.lineOne("Random Rd.")
				.lineTwo("Unit 42")
				.city("Seattle")
				.state(GeographicState.WA)
				.zip("98101")
				.build();
		
		messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
	}
	
	@Test
	void getAllShouldReturnListOfAddresses() throws Exception {
		List<AddressEntity> addressEntities = addressDao.findAll();
		String json = new Gson().toJson(addressEntities);
		
		mockMvc.perform(get("/addresses/all"))
				.andExpect(content().json(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnAddress() throws Exception {
		Long testId = 1L;
		String json = new Gson().toJson(addressDao.findById(testId));
		
		mockMvc.perform(get("/addresses/id?addressId={testId}", testId))
				.andExpect(content().json(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(get("/addresses/id?addressId={testId}", testId))
				.andExpect(jsonPath("$.message", is("AddressEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void addAddressShouldReturnCreatedAddress() throws Exception {
		Long addressId = (long) addressDao.findAll().size() + 1;
		String json = new Gson().toJson(testAddress);
		
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(json))
				.andExpect(content().json(new Gson().toJson(addressDao.findById(addressId))))
				.andExpect(status().isCreated());
	}
	
	@Test
	void addAddressShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		invalidateTestAddress();
		String json = new Gson().toJson(testAddress);
		
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(json))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("lineone.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("linetwo.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("city.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("state.invalid", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("zip.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addAddressShouldReturnInternalErrorIfIdExists() throws Exception {
		testAddress.setAddressId(1L);
		String json = new Gson().toJson(testAddress);
		
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(json))
				.andExpect(jsonPath("$.message", is("Encountered an error while attempting to save")))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void updateAddressShouldReturnUpdatedAddress() throws Exception {
		testAddress.setAddressId(2L);
		
		String json = new Gson().toJson(testAddress);
		
		mockMvc.perform(put("/addresses/update?addressId=2")
				.contentType("application/json")
				.content(json))
				.andExpect(content().json(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateAddressShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		testAddress.setAddressId(2L);
		invalidateTestAddress();
		
		String json = new Gson().toJson(testAddress);

		mockMvc.perform(put("/addresses/update?addressId=2")
				.contentType("application/json")
				.content(json))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("lineone.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("linetwo.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("city.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("state.invalid", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("zip.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteAddressShouldRemoveAddressFromDatabase() throws Exception {
		Long testId = 3L;
		
		mockMvc.perform(delete("/addresses/remove?addressId=" + testId))
				.andExpect(status().isNoContent());
		
		assertThrows(IdNotFoundException.class, () -> addressDao.findById(3L));
	}
	
	@Test
	void deleteAddressShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(delete("/addresses/remove?addressId=" + testId))
				.andExpect(jsonPath("$.message", is("AddressEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	void invalidateTestAddress() {
		testAddress.setLineOne("");
		testAddress.setLineTwo("abcdefghijklmnopqrstuvwxyz");
		testAddress.setCity("");
		testAddress.setState(null);
		testAddress.setZip("981");
	}
}
