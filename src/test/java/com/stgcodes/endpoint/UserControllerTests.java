package com.stgcodes.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.dao.UserDao;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.mappers.UserMapper;
import com.stgcodes.model.User;
import com.stgcodes.utils.sorting.UserComparator;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserDao userDao;
	
	private User testUser;
	private ResourceBundleMessageSource messageSource;
	
	@BeforeEach
	void setup() {
		PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
		testUser = User.builder()
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
	void getAllShouldReturnListOfUsers() throws Exception {
		List<UserEntity> userEntities = userDao.findAll();
		List<User> users = new ArrayList<User>();
		
		userEntities.forEach(e -> users.add(UserMapper.INSTANCE.userEntityToUser(e)));
		users.sort(new UserComparator());
		
		mockMvc.perform(get("/users/all"))
			.andDo(print())
				.andExpect(jsonPath("$[0].firstName", is(users.get(0).getFirstName())))
				.andExpect(jsonPath("$[1].lastName", is(users.get(1).getLastName())))
				.andExpect(jsonPath("$[2].username", is(users.get(2).getUsername())))
				.andExpect(jsonPath("$[3].dateOfBirth", is(users.get(3).getDateOfBirth().toString())))
				.andExpect(jsonPath("$[4].email", is(users.get(4).getEmail())))
				.andExpect(status().isOk());
	}
	
	@Test
	void searchForShouldReturnMatchedUsers() throws Exception {
		UserCriteria criteria = UserCriteria.builder().firstName("RI").build();
		
		mockMvc.perform(get("/users/search")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(criteria)))
				.andExpect(jsonPath("$[0].userId", is(1)))
				.andExpect(jsonPath("$[1].userId", is(3)))
				.andExpect(jsonPath("$[2].userId", is(5)))
				.andExpect(status().isOk());
	}
	
	@Test
	void searchForShouldReturnMatchedUsersAndAllowAllArguments() throws Exception {
		UserCriteria criteria = UserCriteria.builder()
				.firstName("RI")
				.lastName("rI")
				.age(40)
				.gender("male")
				.build();
		
		mockMvc.perform(get("/users/search")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(criteria)))
				.andExpect(jsonPath("$[0].userId", is(1)))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnUser() throws Exception {
		Long testId = 1L;
		UserEntity userEntity = userDao.findById(testId);
		
		mockMvc.perform(get("/users/id?userId={testId}", testId))
				.andExpect(jsonPath("$.firstName", is(userEntity.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(userEntity.getLastName())))
				.andExpect(jsonPath("$.username", is(userEntity.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(userEntity.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(userEntity.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(userEntity.getGender().toString())))
				.andExpect(jsonPath("$.email", is(userEntity.getEmail())))
				.andExpect(jsonPath("$.phones[0].phoneNumber", is(userEntity.getPhones().get(0).getPhoneNumber())))
				.andExpect(status().isOk());
	}
	
	@Test
	void getByIdShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(get("/users/id?userId={testId}", testId))
				.andExpect(jsonPath("$.message", is("UserEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void addUserShouldReturnCreatedUser() throws Exception {
		mockMvc.perform(put("/users/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.firstName", is(testUser.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testUser.getLastName())))
				.andExpect(jsonPath("$.username", is(testUser.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(testUser.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(testUser.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(testUser.getGender().toString())))
				.andExpect(jsonPath("$.email", is(testUser.getEmail())))
				.andExpect(jsonPath("$.phones[0].phoneNumber", is(testUser.getPhones().get(0).getPhoneNumber())))
				.andExpect(jsonPath("$.phones[0].phoneType", is(testUser.getPhones().get(0).getPhoneType().toString())))
				.andExpect(status().isCreated());
	}
	
	@Test
	void addUserShouldReturnBadRequestWithoutPhone() throws Exception {
		testUser.setPhones(Collections.emptyList());
		
		mockMvc.perform(put("/users/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("phones.size", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addUserShouldReturnBadRequestIfMissingRequestBody() throws Exception {
		mockMvc.perform(put("/users/add")
				.content(""))
				.andExpect(jsonPath("$.message", is("Malformed JSON request")))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addUserShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		invalidateTestUser();
		
		mockMvc.perform(put("/users/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("username.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("ssn.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("gender.invalid", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[5].message", is(messageSource.getMessage("email.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void addUserShouldReturnInternalErrorIfUserIdExists() throws Exception {
		testUser.setUserId(1L);
		
		mockMvc.perform(put("/users/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.message", is("Encountered an error while attempting to save")))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void updateUserShouldReturnUpdatedUser() throws Exception {
		Long testId = 2L;
		testUser.setUserId(testId);
		testUser.setUsername("abcdef");
		testUser.setSocialSecurityNumber("776-54-3210");
		testUser.setEmail("who@what.com");
				
		mockMvc.perform(put("/users/update?userId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.firstName", is(testUser.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testUser.getLastName())))
				.andExpect(jsonPath("$.username", is(testUser.getUsername())))
				.andExpect(jsonPath("$.dateOfBirth", is(testUser.getDateOfBirth().toString())))
				.andExpect(jsonPath("$.socialSecurityNumber", is(testUser.getSocialSecurityNumber())))
				.andExpect(jsonPath("$.gender", is(testUser.getGender().toString())))
				.andExpect(jsonPath("$.email", is(testUser.getEmail())))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateUserShouldReturnBadRequestIfRequestBodyInvalid() throws Exception {
		Long testId = 2L;
		testUser.setUserId(testId);
		invalidateTestUser();

		mockMvc.perform(put("/users/update?userId={testId}", testId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(jsonPath("$.subErrors[0].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[1].message", is(messageSource.getMessage("name.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[2].message", is(messageSource.getMessage("username.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[3].message", is(messageSource.getMessage("ssn.format", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[4].message", is(messageSource.getMessage("gender.invalid", null, Locale.US))))
				.andExpect(jsonPath("$.subErrors[5].message", is(messageSource.getMessage("email.format", null, Locale.US))))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteUserShouldRemoveUserFromDatabase() throws Exception {
		Long testId = 4L;
		
		mockMvc.perform(delete("/users/remove?userId={testId}", testId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void deleteUserShouldReturnNotFoundIfIdDoesNotExist() throws Exception {
		Long testId = 0L;
		
		mockMvc.perform(delete("/users/remove?userId={testId}", testId))
				.andExpect(jsonPath("$.message", is("UserEntity was not found with ID " + testId)))
				.andExpect(status().isNotFound());
	}
	
	void invalidateTestUser() {
		testUser.setFirstName("");
		testUser.setLastName("");
		testUser.setUsername("abc");
		testUser.setSocialSecurityNumber("123-45-67890");
		testUser.setGender(null);
		testUser.setEmail("who@whatcom");
	}
}
