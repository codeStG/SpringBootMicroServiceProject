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
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.enums.PhoneType;

@SpringBootTest()
@AutoConfigureMockMvc
class PhoneControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PhoneDao phoneDao;
	
	private Phone testPhone;
	private ResourceBundleMessageSource messageSource;
	
	@BeforeEach
	void setup() {
		testPhone = Phone.builder()
				.phoneNumber("214-214-2142")
				.phoneType(PhoneType.MOBILE)
				.build();
		
		messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
	}
	
	@Test
	void getAllShouldReturnListOfPhones() throws Exception {
		List<PhoneEntity> phoneEntities = phoneDao.findAll();
		
		mockMvc.perform(get("/phones/all"))
				.andExpect(content().json(objectMapper.writeValueAsString(phoneEntities)))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnPhone() throws Exception {
		Long testId = 1L;
		
		mockMvc.perform(get("/phones/id?phoneId={testId}", testId))
				.andExpect(content().json(objectMapper.writeValueAsString(phoneDao.findById(testId))))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(get("/phones/id?phoneId={testId}", testId))
				.andExpect(jsonPath("$.message", is("PhoneEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void addPhoneShouldReturnCreatedPhone() throws Exception {
		Long personId = 3L;
		testPhone.setPhoneNumber("817-718-8177");

		mockMvc.perform(put("/phones/add?personId={personId}", personId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(jsonPath("$.phoneNumber", is(testPhone.getPhoneNumber())))
				.andExpect(jsonPath("$.phoneType", is(testPhone.getPhoneType().toString())))
				.andExpect(jsonPath("$.personEntity.personId", is(personId.intValue())))
				.andExpect(status().isCreated());
	}
	
	@Test
	void addPhoneShouldReturnNotFoundWithoutPersonId() throws Exception {
		mockMvc.perform(put("/phones/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(jsonPath("$.message", is("PersonEntity was not found with ID -1")))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void addPhoneShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		Long personId = 3L;
		invalidateTestPhone();
		
		mockMvc.perform(put("/phones/add?personId={personId}", personId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("phonenumber.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("phonetype.invalid", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addPhoneShouldReturnInternalErrorIfPhoneIdExists() throws Exception {
		Long personId = 3L;
		testPhone.setPhoneId(1L);
		
		mockMvc.perform(put("/phones/add?personId={personId}", personId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(jsonPath("$.message", is("Encountered an error while attempting to save")))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void updatePhoneShouldReturnUpdatedPhone() throws Exception {
		Long testId = 2L;
		testPhone.setPhoneId(testId);
				
		mockMvc.perform(put("/phones/update?phoneId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(content().json(objectMapper.writeValueAsString(phoneDao.findById(testId))))
				.andExpect(status().isOk());
	}
	
	@Test
	void updatePhoneShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		Long testId = 2L;
		testPhone.setPhoneId(testId);
		invalidateTestPhone();

		mockMvc.perform(put("/phones/update?phoneId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPhone)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("phonenumber.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("phonetype.invalid", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deletePhoneShouldRemovePhoneFromDatabase() throws Exception {
		Long testId = 3L;
		
		mockMvc.perform(delete("/phones/remove?phoneId={testId}", testId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void deletePhoneShouldReturnBadRequestIfDeletingLastPhone() throws Exception {
		Long testId = 1L;
		
		mockMvc.perform(delete("/phones/remove?phoneId={testId}", testId))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deletePhoneShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(delete("/phones/remove?phoneId=" + testId))
				.andExpect(jsonPath("$.message", is("PhoneEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	void invalidateTestPhone() {
		testPhone.setPhoneNumber("");
		testPhone.setPhoneType(null);
	}
}
