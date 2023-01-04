package com.tfg.look4pop.web.app.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerMockTest {
	
	@Autowired
	private MockMvc mockMvc;
	 
	@BeforeEach
	void setUp() {
		//NA
	}
	
	// Test login page OK
	@DisplayName("LoginController -> login page OK")
	@Test
	void testLoginPageOk() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/login"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("login"));
	}
	
	// Test login KO
	@DisplayName("LoginController -> login KO")
	@Test
	void testLoginKo() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/login")
				.param("error", "")
				.with(csrf()))
	        .andExpect(status().isOk())
	        .andExpect(view().name("login"))
		 	.andExpect(model().attributeExists("error"));
	}
	
	// Test logout OK
	@DisplayName("LoginController -> logout OK")
	@Test
	void testLogoutOk() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/login")
				.param("logout", "")
				.with(csrf()))
	        .andExpect(status().isOk())
	        .andExpect(view().name("login"))
		 	.andExpect(model().attributeExists("success"));
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
