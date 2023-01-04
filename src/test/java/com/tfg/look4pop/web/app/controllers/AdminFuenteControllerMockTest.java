package com.tfg.look4pop.web.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.service.IFuenteService;

@SpringBootTest
public class AdminFuenteControllerMockTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	FilterChainProxy springSecurityFilterChain;
	
	@Mock
	private IFuenteService mockFuenteService;
	
	@InjectMocks // auto inject mockFuenteService
    private AdminFuenteController adminFuenteController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		adminFuenteController = context.getBean(AdminFuenteController.class);
		
		this.mockMvc = MockMvcBuilders
				  .standaloneSetup(adminFuenteController)
				  //.alwaysDo(print())
			      .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
			      .build();
		MockitoAnnotations.initMocks(this);
	}
	
	// Test acceso
	@DisplayName("AdminFuenteController -> acceso OK")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testAccesoOk() throws Exception {
        this.mockMvc
        	.perform(MockMvcRequestBuilders.get("/admin/fuentes"))
        	.andExpect(status().isOk());
	}
	
	// Test acceso
	@DisplayName("AdminFuenteController -> acceso KO")
	@Test
	@WithMockUser(roles = "USER") // No Administrador
	void testAccesoKo() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/fuentes"))
	        .andExpect(status().isForbidden());
	}
	
	// Test crearFuente
	@DisplayName("AdminFuenteController -> crearFuente")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testCrearFuente() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/fuentes/alta"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/fuentes/alta"));
	}
		
	// Test eliminarFuente
	@DisplayName("AdminFuenteController -> eliminarFuente")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testEliminarFuente() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/fuentes/eliminar"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/fuentes/eliminar"));
	}
	
	
	// Test getFuenteAltaForm
	@DisplayName("AdminFuenteController -> crearFuenteForm")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testGetFuenteAltaForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/fuentes/alta/form"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/fuentes/alta"));
	}
		
	// Test getFuenteEliminarForm
	@DisplayName("AdminFuenteController -> eliminarFuenteForm")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testGetFuenteEliminarForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/fuentes/eliminar/form"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/fuentes/eliminar"));
	}
	
	// Test procesarAltaFuente
	@DisplayName("AdminFuenteController -> procesarAltaFuente")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarAltaFuente() throws Exception {
		// Error
		FuenteFormDTO fuenteFormError = new FuenteFormDTO();
		fuenteFormError.setTipo("censo");
		fuenteFormError.setSubtipo("derecho");
		fuenteFormError.setAnioP(null);
		fuenteFormError.setProcedenciaDatos(null);
		fuenteFormError.setTpAccion("A");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/fuentes/alta/form")
				.flashAttr("fuenteForm", fuenteFormError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/fuentes/alta"));
		
		// Fuente existente
		FuenteFormDTO fuenteFormExist = new FuenteFormDTO();
		fuenteFormExist.setTipo("censo");
		fuenteFormExist.setSubtipo("derecho");
		fuenteFormExist.setAnioC("2090");
		fuenteFormExist.setAnioP(null);
		fuenteFormExist.setProcedenciaDatos(null);
		fuenteFormExist.setTpAccion("A");
		
		Fuente fuente = new Fuente("censo", "derecho", "2090", null);
		when(mockFuenteService.getAnioByTpFuente(fuenteFormExist)).thenReturn(fuente);
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/fuentes/alta/form")
				.flashAttr("fuenteForm", fuenteFormExist)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/fuentes/alta"));
		
		// Alta fuente
		when(mockFuenteService.getAnioByTpFuente(fuenteFormExist)).thenReturn(null);
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/fuentes/alta/form")
				.flashAttr("fuenteForm", fuenteFormExist)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/fuentes/alta"));
	}
	
	// Test procesarEliminacionFuente
	@DisplayName("AdminFuenteController -> procesarEliminacionFuente")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarEliminacionFuente() throws Exception {
		// Eliminacion fuente
		FuenteFormDTO fuenteFormDel = new FuenteFormDTO();
		fuenteFormDel.setTipo("censo");
		fuenteFormDel.setSubtipo("derecho");
		fuenteFormDel.setAnioC("2090");
		fuenteFormDel.setAnioP(null);
		fuenteFormDel.setProcedenciaDatos(null);
		fuenteFormDel.setTpAccion("E");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/fuentes/eliminar/form")
				.flashAttr("fuenteForm", fuenteFormDel)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/fuentes/eliminar"));
	
		assertEquals("censo", fuenteFormDel.getTipo());
		assertEquals("derecho", fuenteFormDel.getSubtipo());
		assertEquals("2090", fuenteFormDel.getAnioC());
		assertNull(fuenteFormDel.getAnioP());
		assertNull(fuenteFormDel.getProcedenciaDatos());
		assertEquals("E", fuenteFormDel.getTpAccion());
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
