package com.tfg.look4pop.web.app.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class AdminControllerMockTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	FilterChainProxy springSecurityFilterChain;
	
	@Mock
	final DataSource dataSource = null;
	
	@Mock
	final Connection connection = null;
	
    @InjectMocks // auto inject dataSource and connection
	private AdminController adminController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		
		adminController = context.getBean(AdminController.class);
		
		this.mockMvc = MockMvcBuilders
				  .standaloneSetup(adminController)
				  //.alwaysDo(print())
			      .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
			      .build();
		MockitoAnnotations.initMocks(this);
		
		when(dataSource.getConnection()).thenReturn(connection);
	}
	
	// Test acceso
	@DisplayName("AdminController -> acceso OK")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testAccesoOk() throws Exception {
        this.mockMvc
        	.perform(MockMvcRequestBuilders.get("/admin"))
        	.andExpect(status().isOk());
	}
	
	// Test acceso
	@DisplayName("AdminController -> acceso KO")
	@Test
	@WithMockUser(roles = "USER") // No Administrador
	void testAccesoKo() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin"))
	        .andExpect(status().isForbidden());
	}
	
	// Test upload
	@DisplayName("AdminController -> upload")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testUpload() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/upload"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/cargar_script"));
	}
	
	// Test procesarScript
	@DisplayName("AdminController -> procesarScript")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarScript() throws Exception {
		// Fichero vacio
		MultipartFile multipartFileVacio = new MockMultipartFile("file", "script.sql", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/admin/upload")
				.file((MockMultipartFile) multipartFileVacio)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/cargar_script"));
		
		// Extension erronea
		MultipartFile multipartFileExtError = new MockMultipartFile("file", "script.txt", MediaType.TEXT_PLAIN_VALUE, "SELECT * FROM FUENTE;".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/admin/upload")
				.file((MockMultipartFile) multipartFileExtError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/cargar_script"));
		
		// Procesado error lanzar script
		MultipartFile multipartFileOk = new MockMultipartFile("file", "script.sql", MediaType.TEXT_PLAIN_VALUE, "SELECT * FROM FUENTE;".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/admin/upload")
				.file((MockMultipartFile) multipartFileOk)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/cargar_script"));
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
