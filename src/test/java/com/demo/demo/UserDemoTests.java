package com.demo.demo;

import java.util.UUID;

import com.demo.demo.model.User;
import com.demo.demo.model.User.Country;
import com.demo.demo.model.User.State;
import com.demo.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserDemoTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;

	private User user = User.builder()
		.id(UUID.fromString("7c000d9c-2a1b-42c9-b69b-d71d892e2e23"))
		.firstName("Henry")
		.lastName("Ton")
		.npiNumber(1234)
		.telephoneNumber("4071112222")
		.email("test@gmail.com")
		.address1("Spring Ln.")
		.city("Orlando")
		.state(State.FL)
		.zipCode("32801")
		.country(Country.USA)
		.build();

	@BeforeEach
	void init() {
		userRepository.deleteAll();
	}

	@Test
	void contextLoads() {

	}

	@Test
	void saveUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
			.post("/register")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(user)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Henry"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Ton"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.npiNumber").value("1234"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.telephoneNumber").value("4071112222"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.address1").value("Spring Ln."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Orlando"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.state").value("FL"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("32801"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.country").value("USA"));
	}

	@Test
	void saveUserFailsIfUserExists() throws Exception {
		userRepository.save(user);
		mockMvc.perform(MockMvcRequestBuilders
			.post("/register")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(user)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").doesNotHaveJsonPath())
			.andExpect(MockMvcResultMatchers.jsonPath("$.lasttName").doesNotHaveJsonPath())
			.andExpect(MockMvcResultMatchers.jsonPath("$.npiNumber").doesNotHaveJsonPath());
	}

	@Test
	void getUser() throws Exception {
		User savedUser = userRepository.save(user);

		mockMvc.perform(MockMvcRequestBuilders
			.get("/user")
			.contentType("application/json")
			.param("id", savedUser.getId().toString()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Henry"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Henry"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Ton"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.npiNumber").value("1234"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.telephoneNumber").value("4071112222"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.address1").value("Spring Ln."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Orlando"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.state").value("FL"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("32801"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.country").value("USA"));
	}
}
