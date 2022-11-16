package com.stgcodes.endpoint;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;

@SpringBootTest()
@AutoConfigureMockMvc
class AddressControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		
		mockMvc.perform(get("/addresses/all"))
				.andExpect(content().json(objectMapper.writeValueAsString(addressEntities)))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnAddress() throws Exception {
		Long testId = 1L;
		
		mockMvc.perform(get("/addresses/id?addressId={testId}", testId))
				.andExpect(content().json(objectMapper.writeValueAsString(addressDao.findById(testId))))
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
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testAddress)))
				.andExpect(jsonPath("$.lineOne", is(testAddress.getLineOne())))
				.andExpect(jsonPath("$.lineTwo", is(testAddress.getLineTwo())))
				.andExpect(jsonPath("$.city", is(testAddress.getCity())))
				.andExpect(jsonPath("$.state", is(testAddress.getState().toString())))
				.andExpect(jsonPath("$.zip", is(testAddress.getZip())))
				.andExpect(status().isCreated());
	}
	
	@Test
	void addAddressShouldReturnBadRequestIfMissingRequestBody() throws Exception {
		mockMvc.perform(put("/addresses/add")
				.content(""))
				.andExpect(jsonPath("$.message", is("Malformed JSON request")))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addAddressShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		invalidateTestAddress();
		
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testAddress)))
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
		
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testAddress)))
				.andExpect(jsonPath("$.message", is("Encountered an error while attempting to save")))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void updateAddressShouldReturnUpdatedAddress() throws Exception {
		Long testId = 2L;
		testAddress.setAddressId(testId);
		
		mockMvc.perform(put("/addresses/update?addressId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testAddress)))
				.andExpect(jsonPath("$.addressId", is(testAddress.getAddressId().intValue())))
				.andExpect(jsonPath("$.lineOne", is(testAddress.getLineOne())))
				.andExpect(jsonPath("$.lineTwo", is(testAddress.getLineTwo())))
				.andExpect(jsonPath("$.city", is(testAddress.getCity())))
				.andExpect(jsonPath("$.state", is(testAddress.getState().toString())))
				.andExpect(jsonPath("$.zip", is(testAddress.getZip())))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateAddressShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		Long testId = 2L;
		testAddress.setAddressId(testId);
		invalidateTestAddress();
		
		mockMvc.perform(put("/addresses/update?addressId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testAddress)))
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
		
		mockMvc.perform(delete("/addresses/remove?addressId={testId}", testId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void deleteAddressShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(delete("/addresses/remove?addressId={testId}", testId))
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
