package com.stgcodes.endpoint;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
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
import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PersonDao personDao;
	
	private Person testPerson;
	private ResourceBundleMessageSource messageSource;
	
	@BeforeEach
	void setup() {
		PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
		testPerson = Person.builder()
				.firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(List.of(testPhone))
                .build();
		
		messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
	}
	
	@Test
	void getAllShouldReturnListOfPeople() throws Exception {
		List<PersonEntity> personEntities = personDao.findAll();
		
		mockMvc.perform(get("/people/all"))
				.andExpect(jsonPath("$[0].firstName", is(personEntities.get(0).getFirstName())))
				.andExpect(jsonPath("$[1].lastName", is(personEntities.get(1).getLastName())))
				.andExpect(jsonPath("$[2].username", is(personEntities.get(2).getUsername())))
				.andExpect(jsonPath("$[3].dateOfBirth", is(personEntities.get(3).getDateOfBirth().toString())))
				.andExpect(jsonPath("$[4].email", is(personEntities.get(4).getEmail())))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnPerson() throws Exception {
		Long testId = 1L;
		PersonEntity personEntity = personDao.findById(testId);
		
		mockMvc.perform(get("/people/id?personId={testId}", testId))
				.andExpect(jsonPath("$.firstName", is(personEntity.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(personEntity.getLastName())))
				.andExpect(jsonPath("$.username", is(personEntity.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(personEntity.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(personEntity.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(personEntity.getGender().toString())))
				.andExpect(jsonPath("$.email", is(personEntity.getEmail())))
				.andExpect(jsonPath("$.phones[0].phoneNumber", is(personEntity.getPhones().get(0).getPhoneNumber())))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(get("/people/id?personId={testId}", testId))
				.andExpect(jsonPath("$.message", is("PersonEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void addPersonShouldReturnCreatedPerson() throws Exception {
		mockMvc.perform(put("/people/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
				.andExpect(jsonPath("$.firstName", is(testPerson.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testPerson.getLastName())))
				.andExpect(jsonPath("$.username", is(testPerson.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(testPerson.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(testPerson.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(testPerson.getGender().toString())))
				.andExpect(jsonPath("$.email", is(testPerson.getEmail())))
				.andExpect(jsonPath("$.phones[0].phoneNumber", is(testPerson.getPhones().get(0).getPhoneNumber())))
				.andExpect(jsonPath("$.phones[0].phoneType", is(testPerson.getPhones().get(0).getPhoneType().toString())))
				.andExpect(status().isCreated());
	}
	
	@Test
	void addPersonShouldReturnBadRequestWithoutPhone() throws Exception {
		testPerson.setPhones(Collections.emptyList());
		
		mockMvc.perform(put("/people/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("phones.size", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addPersonShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		invalidateTestPerson();
		
		mockMvc.perform(put("/people/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("username.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("ssn.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("gender.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[5].message", is(messageSource.getMessage("email.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addPersonShouldReturnInternalErrorIfPersonIdExists() throws Exception {
		testPerson.setPersonId(1L);
		
		mockMvc.perform(put("/people/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
				.andExpect(jsonPath("$.message", is("Encountered an error while attempting to save")))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void updatePersonShouldReturnUpdatedPerson() throws Exception {
		Long testId = 2L;
		testPerson.setPersonId(testId);
		testPerson.setUsername("abcdef");
		testPerson.setSocialSecurityNumber("776-54-3210");
		testPerson.setEmail("who@what.com");
				
		mockMvc.perform(put("/people/update?personId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
		.andDo(print())
				.andExpect(jsonPath("$.firstName", is(testPerson.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testPerson.getLastName())))
				.andExpect(jsonPath("$.username", is(testPerson.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(testPerson.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(testPerson.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(testPerson.getGender().toString())))
				.andExpect(jsonPath("$.email", is(testPerson.getEmail())))
				.andExpect(status().isOk());
	}
	
	@Test
	void updatePersonShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		Long testId = 2L;
		testPerson.setPersonId(testId);
		invalidateTestPerson();

		mockMvc.perform(put("/people/update?personId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testPerson)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("username.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("ssn.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("gender.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[5].message", is(messageSource.getMessage("email.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deletePersonShouldRemovePersonFromDatabase() throws Exception {
		Long testId = 3L;
		
		mockMvc.perform(delete("/people/remove?personId={testId}", testId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void deletePersonShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(delete("/people/remove?personId=" + testId))
				.andExpect(jsonPath("$.message", is("PersonEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	void invalidateTestPerson() {
		testPerson.setFirstName("");
		testPerson.setLastName("");
		testPerson.setUsername("abc");
		testPerson.setSocialSecurityNumber("123-45-67890");
		testPerson.setGender(null);
		testPerson.setEmail("who@whatcom");
	}
}
