package com.stgcodes.endpoint;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	private ObjectMapper objectMapper;
	
	@Autowired
	private AddressDao addressDao;
	
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
		AddressEntity addressEntity = addressDao.findById(testId);
		String json = new Gson().toJson(addressEntity);
		
		mockMvc.perform(get("/addresses/id?addressId=" + testId))
				.andExpect(content().json(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void addAddressShouldReturnCreatedAddress() throws Exception {
		Address newAddress = Address.builder()
				.lineOne("Random Rd.")
				.lineTwo("Unit 42")
				.city("Seattle")
				.state(GeographicState.WA)
				.zip("98101")
				.build();
		
		List<AddressEntity> addresses = addressDao.findAll();
		Long addressId = (long) addresses.size() + 1;
				
		mockMvc.perform(put("/addresses/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newAddress)))
				.andExpect(content().json(new Gson().toJson(addressDao.findById(addressId))))
				.andExpect(status().isCreated());
	}
	
	@Test
	void updateAddressShouldReturnUpdatedAddress() throws Exception {
		Address addressToUpdate = Address.builder()
				.addressId(2L)
				.lineOne("New Ave.")
				.lineTwo("")
				.city("Rockwall")
				.state(GeographicState.TX)
				.zip("75032")
				.build();
		
		String json = new Gson().toJson(addressToUpdate);
		
		mockMvc.perform(put("/addresses/update?addressId=2")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(addressToUpdate)))
				.andDo(print())
				.andExpect(content().json(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteAddressShouldRemoveAddressFromDatabase() throws Exception {
		Long testId = 3L;
		
		mockMvc.perform(delete("/addresses/remove?addressId=" + testId))
				.andExpect(status().isNoContent());
		
		assertThrows(IdNotFoundException.class, () -> addressDao.findById(3L));
	}
}
