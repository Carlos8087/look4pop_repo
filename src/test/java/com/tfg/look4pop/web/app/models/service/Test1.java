package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.entity.Territorio;
import com.tfg.look4pop.web.app.models.mapper.ITerritorioMapper;

@SpringBootTest
public class Test1 {
	
	@Mock
	private ITerritorioMapper mockTerritorioMapper;
	
	@InjectMocks // auto inject mockTerritorioMapper
    private ITerritorioService territorioService = new TerritorioServiceImpl();
	
	//@Autowired
    //ITerritorioService territorioService;
	
	

   
	@BeforeEach
	void setUp() {
		Territorio mockTerritorio = new Territorio();
		
		mockTerritorio.setIdTerritorio(47);
		mockTerritorio.setNombreActual("Barcelona");
		
		/*
		private Integer idTerritorio;
		private String nombreActual;
		private String nombreCortoActual;
		private String tipoTerritorio;
		private String codigoOficial;
		private Integer e;
		private Integer r;
		private Integer p;
		private Integer m;
		private Integer mv;
		private Integer c;
		private Integer au;
		private Integer auv;
		private Integer i;
		private Integer zc;
		private Integer ac;
		private Integer du;
		private Integer pj;
		private Integer zf;
		private Double km2;
		private Double este;
		private Double oeste;
		private Double norte;
		private Double sur;
		private Integer x;
		private Integer y;
		private Integer capital;
		private byte activo;
		*/
		
		when(mockTerritorioMapper.getTerritorioById("47")).thenReturn(mockTerritorio);
		
	}
	
	@DisplayName("getTerritorioById")
    @Test
    void testGetTerritorioById() {
    	// Test
    	//Territorio test = territorioService.getTerritorioById("47");
        //assertEquals("Barcelona", test.getNombreActual());
		
		Territorio test = territorioService.getTerritorioById("47");
		assertEquals("Barcelona", test.getNombreActual());
    }
    
	@DisplayName("getTerritorioIdByNivelAndAmbitoGenLst")
    @Test
    void testGetTerritorioIdByNivelAndAmbitoGenLst() {
    	// Test
    	//Territorio test = territorioService.getTerritorioById("47");
        //assertEquals("Barcelona", test.getNombreActual());
		
		//Territorio test = territorioService.getTerritorioIdByNivelAndAmbitoGenLst("47");
		//assertEquals("Barcelon", test.getNombreActual());
    }
    
    @AfterEach
    void tearDown() {
    	
    }

}
