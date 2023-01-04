package com.tfg.look4pop.web.app.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tfg.look4pop.web.app.models.dto.UsuarioFormDTO;
import com.tfg.look4pop.web.app.models.entity.Usuario;
import com.tfg.look4pop.web.app.models.service.IUsuarioService;

@SpringBootTest
public class AdminUsuarioControllerMockTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	FilterChainProxy springSecurityFilterChain;
	
	@Mock
	private IUsuarioService mockUsuarioService;
	
	@InjectMocks // auto inject mockUsuarioService
    private AdminUsuarioController adminUsuarioController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		adminUsuarioController = context.getBean(AdminUsuarioController.class);
		
		this.mockMvc = MockMvcBuilders
				  .standaloneSetup(adminUsuarioController)
				  //.alwaysDo(print())
			      .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
			      .build();
		MockitoAnnotations.initMocks(this);
		
		// 'selectUsuarios' model attribute
		Date date = new Date();
		Usuario userNoAdmin = new Usuario(30, "user30", "1234", 1, "admin99", new Timestamp(date.getTime()), null, null);
		Usuario userAdmin1 = new Usuario(31, "admin31", "9876", 1, "admin99", new Timestamp(date.getTime()), null, null);
		Usuario userAdmin2 = new Usuario(70, "admin70", "1234", 1, "admin99", new Timestamp(date.getTime()), null, null);
		
		List<Usuario> mockUsuariosAll = new ArrayList<Usuario>();
		mockUsuariosAll.add(userNoAdmin);
		mockUsuariosAll.add(userAdmin1);
		mockUsuariosAll.add(userAdmin2);
		
		when(mockUsuarioService.getUsuarioLst()).thenReturn(mockUsuariosAll);
	}
	
	// Test acceso
	@DisplayName("AdminUsuarioController -> acceso OK")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testAccesoOk() throws Exception {
        this.mockMvc
        	.perform(MockMvcRequestBuilders.get("/admin/usuarios"))
        	.andExpect(status().isOk());
	}
	
	// Test acceso
	@DisplayName("AdminUsuarioController -> acceso KO")
	@Test
	@WithMockUser(roles = "USER") // No Administrador
	void testAccesoKo() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios"))
	        .andExpect(status().isForbidden());
	}
	
	// Test crearUsuario
	@DisplayName("AdminUsuarioController -> crearUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testCrearUsuario() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/alta"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/alta"));
	}
	
	// Test modificarUsuario
	@DisplayName("AdminUsuarioController -> modificarUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testModificarUsuario() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/modificar"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/modificar"));
	}
		
	// Test eliminarUsuario
	@DisplayName("AdminUsuarioController -> eliminarUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testEliminarUsuario() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/eliminar"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/eliminar"));
	}
	
	// Test getUsuarioAltaForm
	@DisplayName("AdminUsuarioController -> crearUsuarioForm")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testGetUsuarioAltaForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/alta/form"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/alta"));
	}
	
	// Test getUsuarioModificarForm
	@DisplayName("AdminUsuarioController -> modificarUsuarioForm")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testGetUsuarioModificarForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/modificar/form"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/modificar"));
	}
		
	// Test getUsuarioEliminarForm
	@DisplayName("AdminUsuarioController -> eliminarUsuarioForm")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testGetUsuarioEliminarForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/admin/usuarios/eliminar/form"))
			.andExpect(status().isOk())
	        .andExpect(view().name("admin/usuarios/eliminar"));
	}
	
	// Test procesarAltaUsuario
	@DisplayName("AdminUsuarioController -> procesarAltaUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarAltaUsuario() throws Exception {
		// Error
		UsuarioFormDTO usuarioFormError = new UsuarioFormDTO();
		usuarioFormError.setTpAccion("A");
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/alta/form")
				.flashAttr("usuarioForm", usuarioFormError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/alta"));
		
		// Usuario existente
		Date date = new Date();
		Usuario userAdmin = new Usuario(70, "admin70", "1234", 1, "admin99", new Timestamp(date.getTime()), null, null);
		when(mockUsuarioService.getUsuarioByUsername("admin70")).thenReturn(userAdmin);
		
		UsuarioFormDTO usuarioFormExist = new UsuarioFormDTO();
		usuarioFormExist.setUsername("admin70");
		usuarioFormExist.setPassword("1234");
		usuarioFormExist.setTpAccion("A");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/alta/form")
				.flashAttr("usuarioForm", usuarioFormExist)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/alta"));
	}
	
	// Test procesarModificacionUsuario
	@DisplayName("AdminUsuarioController -> procesarModificacionUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarModificacionUsuario() throws Exception {
		// Error
		UsuarioFormDTO usuarioFormError = new UsuarioFormDTO();
		usuarioFormError.setTpAccion("M");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/modificar/form")
				.flashAttr("usuarioForm", usuarioFormError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/modificar"));
		
		// Modificacion usuario
		Date date = new Date();
		Usuario userAdmin = new Usuario(70, "admin70", "1234", 1, "admin99", new Timestamp(date.getTime()), null, null);
		when(mockUsuarioService.getUsuarioByUsername("admin70")).thenReturn(userAdmin);
		
		UsuarioFormDTO usuarioFormMod = new UsuarioFormDTO();
		usuarioFormMod.setIdUsuarioSelect("70");
		usuarioFormMod.setUsername("admin70");
		usuarioFormMod.setPassword("1234");
		usuarioFormMod.setEnabled("1");
		usuarioFormMod.setTpAccion("M");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/modificar/form")
				.flashAttr("usuarioForm", usuarioFormMod)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/modificar"));
	}
	
	// Test procesarEliminacionUsuario
	@DisplayName("AdminUsuarioController -> procesarEliminacionUsuario")
	@Test
	@WithMockUser(roles = "ADMIN") // Administrador
	void testProcesarEliminacionUsuario() throws Exception {
		// Error
		UsuarioFormDTO usuarioFormError = new UsuarioFormDTO();
		usuarioFormError.setTpAccion("E");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/eliminar/form")
				.flashAttr("usuarioForm", usuarioFormError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/eliminar"));
		
		// Eliminacion usuario
		Date date = new Date();
		Usuario userAdmin = new Usuario(70, "admin70", "1234", 1, "admin99", new Timestamp(date.getTime()), null, null);
		when(mockUsuarioService.getUsuarioByUsername("admin70")).thenReturn(userAdmin);
		
		UsuarioFormDTO usuarioFormDel = new UsuarioFormDTO();
		usuarioFormDel.setIdUsuarioSelect("70");
		usuarioFormDel.setUsername("admin70");
		usuarioFormDel.setPassword("1234");
		usuarioFormDel.setEnabled("1");
		usuarioFormDel.setTpAccion("E");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/admin/usuarios/eliminar/form")
				.flashAttr("usuarioForm", usuarioFormDel)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/usuarios/eliminar"));
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
