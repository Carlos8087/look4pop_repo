package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.entity.Usuario;
import com.tfg.look4pop.web.app.models.mapper.IUsuarioMapper;

@SpringBootTest
public class UsuarioServiceMockTest {
	
	@Mock
	private IUsuarioMapper mockUsuarioMapper;
	
	@InjectMocks // auto inject mockUsuarioMapper
    private IUsuarioService usuarioService = new UsuarioServiceImpl();
	
	@BeforeEach
	void setUp() {
		//NA
	}	
	
	// Test getUsuarioById
	@DisplayName("UsuarioService -> getUsuarioById")
    @Test
    void testGetUsuarioById() {
		Date date = new Date();	
		Usuario mockUsuario100 = new Usuario(100, "testMock", "1234", 1, "admin0", new Timestamp(date.getTime()), null, null);
				
		when(mockUsuarioMapper.getUsuarioById(100)).thenReturn(mockUsuario100);
				
		Usuario test = usuarioService.getUsuarioById(100);
		assertEquals(100, test.getId());
		assertEquals("testMock", test.getUsername());
    }
	
	// Test getUsuarioByUsername
	@DisplayName("UsuarioService -> getUsuarioByUsername")
    @Test
    void testGetUsuarioByUsername() {
		Date date = new Date();	
		Usuario mockUsuarioTestMock = new Usuario(100, "testMock", "1234", 1, "admin0", new Timestamp(date.getTime()), null, null);
		
		when(mockUsuarioMapper.getUsuarioByUsername("testMock")).thenReturn(mockUsuarioTestMock);
		
		Usuario test = usuarioService.getUsuarioByUsername("testMock");
		assertEquals(100, test.getId());
		assertEquals("testMock", test.getUsername());
    }
	
	// Test getUsuarioLst
	@DisplayName("UsuarioService -> getUsuarioLst")
    @Test
    void testGetUsuarioLst() {
		Date date = new Date();	
		Usuario usuario1 = new Usuario(1, "admin1", "1234", 1, "admin0", new Timestamp(date.getTime()), null, null);
		Usuario usuario2 = new Usuario(2, "admin2", "9876", 1, "admin0", new Timestamp(date.getTime()), null, null);
		
		List<Usuario> mockUsuariosAll = new ArrayList<Usuario>();
		mockUsuariosAll.add(usuario1);
		mockUsuariosAll.add(usuario2);
		
		when(mockUsuarioMapper.getUsuarioLst()).thenReturn(mockUsuariosAll);
		
		List<Usuario> test = usuarioService.getUsuarioLst();
		assertEquals(2, test.size());
    }
	
	// Test insert
	@DisplayName("UsuarioService -> insert")
    @Test
    void testInsert() {
		Date date = new Date();	
		Usuario usuario8 = new Usuario(8, "admin8", "5566", 1, "admin0", new Timestamp(date.getTime()), null, null);
		usuarioService.insert(usuario8);
		verify(mockUsuarioMapper, times(1)).insert(usuario8);
    }
	
	// Test update
	@DisplayName("UsuarioService -> update")
	@Test
	void testUpdate() {
		Date date = new Date();	
		Usuario usuario8 = new Usuario(8, "admin8", "5566", 1, "admin0", new Timestamp(date.getTime()), null, null);
		usuarioService.update(usuario8);
		verify(mockUsuarioMapper, times(1)).update(usuario8);
	}
	
	// Test delete
	@DisplayName("UsuarioService -> delete")
    @Test
    void testDelete() {
		Date date = new Date();	
		Usuario usuario8 = new Usuario(8, "admin8", "5566", 1, "admin0", new Timestamp(date.getTime()), null, null);
		usuarioService.delete(usuario8);
		verify(mockUsuarioMapper, times(1)).delete(usuario8);
    }
	
	@AfterEach
    void tearDown() {
    	//NA
    }

}
