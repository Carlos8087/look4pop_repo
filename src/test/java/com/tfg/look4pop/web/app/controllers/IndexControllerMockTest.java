package com.tfg.look4pop.web.app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerMockTest {
	
	@Autowired
	private MockMvc mockMvc;
	 
	@BeforeEach
	void setUp() {
		//NA
	}
	
	// Test index
	@DisplayName("IndexController -> index1")
	@Test
	void testIndex1() throws Exception {
		this.mockMvc
			.perform(get("/"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("index"))
	        .andExpect(model().attributeExists("tituloPagina"))
	        .andExpect(model().attributeExists("clsInicio"));
	}
	
	// Test index
	@DisplayName("IndexController -> index2")
	@Test
	void testIndex2() throws Exception {
		this.mockMvc
			.perform(get("/index"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("index"))
	        .andExpect(model().attributeExists("tituloPagina"))
	        .andExpect(model().attributeExists("clsInicio"));
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
