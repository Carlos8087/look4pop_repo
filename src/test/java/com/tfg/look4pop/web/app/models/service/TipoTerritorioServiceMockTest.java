package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;
import com.tfg.look4pop.web.app.models.mapper.ITipoTerritorioMapper;

@SpringBootTest
public class TipoTerritorioServiceMockTest {
	
	@Mock
	private ITipoTerritorioMapper mockTipoTerritorioMapper;
	
	@InjectMocks // auto inject mockTipoTerritorioMapper
    private ITipoTerritorioService tipoTerritorioService = new TipoTerritorioServiceImpl();
	
	@BeforeEach
	void setUp() {
		//NA
	}	
	
	// Test getTipoTerritorioById
	@DisplayName("TipoTerritorioService -> getTipoTerritorioById")
    @Test
    void testGetTipoTerritorioById() {
		TipoTerritorio mockTipoTerritorioProv = new TipoTerritorio("P", "Provincia", null, "E,R,P");
		
		when(mockTipoTerritorioMapper.getTipoTerritorioById("P")).thenReturn(mockTipoTerritorioProv);
		
		TipoTerritorio test = tipoTerritorioService.getTipoTerritorioById("P");
		assertEquals("Provincia", test.getNombre());
    }
	
	// Test getTipoTerritorioNivelLst
	@DisplayName("TipoTerritorioService -> getTipoTerritorioNivelLst")
    @Test
    void testGetTipoTerritorioNivelLst() {
		TipoTerritorio tipoTerritorioAU = new TipoTerritorio("AU", "Area Urbana", null, "E,R,AU");
		TipoTerritorio tipoTerritorioC = new TipoTerritorio("C", "Comarca", null, "E,R,C");
		TipoTerritorio tipoTerritorioE = new TipoTerritorio("E", "Estado", null, "E");
		TipoTerritorio tipoTerritorioI = new TipoTerritorio("I", "Isla", null, "E,R,P,I");
		TipoTerritorio tipoTerritorioM = new TipoTerritorio("M", "Municipio", null, "E,R,P,AU,C,I,PJ,M");
		TipoTerritorio tipoTerritorioP = new TipoTerritorio("P", "Provincia", null, "E,R,P");
		TipoTerritorio tipoTerritorioPJ = new TipoTerritorio("PJ", "Partido Judicial", null, "E,R,P,PJ");
		TipoTerritorio tipoTerritorioR = new TipoTerritorio("R", "Región", null, "E,R");
		TipoTerritorio tipoTerritorioZC = new TipoTerritorio("ZC", "Zona Comunal", null, "E,R,P,C,PJ,ZC");
		
		List<TipoTerritorio> mockTipoTerritorioAll = new ArrayList<TipoTerritorio>();
		mockTipoTerritorioAll.add(tipoTerritorioAU);
		mockTipoTerritorioAll.add(tipoTerritorioC);
		mockTipoTerritorioAll.add(tipoTerritorioE);
		mockTipoTerritorioAll.add(tipoTerritorioI);
		mockTipoTerritorioAll.add(tipoTerritorioM);
		mockTipoTerritorioAll.add(tipoTerritorioP);
		mockTipoTerritorioAll.add(tipoTerritorioPJ);
		mockTipoTerritorioAll.add(tipoTerritorioR);
		mockTipoTerritorioAll.add(tipoTerritorioZC);
		
		when(mockTipoTerritorioMapper.getTipoTerritorioNivelLst()).thenReturn(mockTipoTerritorioAll);
		
		List<TipoTerritorio> test = tipoTerritorioService.getTipoTerritorioNivelLst();
		assertEquals(9, test.size());
    }
	
	// Test getTipoTerritorioAmbitoGenLst
	@DisplayName("TipoTerritorioService -> getTipoTerritorioAmbitoGenLst")
    @Test
    void testGetTipoTerritorioAmbitoGenLst() {
		TipoTerritorio tipoTerritorioE = new TipoTerritorio("E", "Estado", null, "E");
		TipoTerritorio tipoTerritorioR = new TipoTerritorio("R", "Región", null, "E,R");
		TipoTerritorio tipoTerritorioP = new TipoTerritorio("P", "Provincia", null, "E,R,P");
		
		List<TipoTerritorio> mockTipoTerritorioAmbGen = new ArrayList<TipoTerritorio>();
		mockTipoTerritorioAmbGen.add(tipoTerritorioE);
		mockTipoTerritorioAmbGen.add(tipoTerritorioR);
		mockTipoTerritorioAmbGen.add(tipoTerritorioP);
		
		when(mockTipoTerritorioMapper.getTipoTerritorioById("P")).thenReturn(tipoTerritorioP);
		when(mockTipoTerritorioMapper.getTipoTerritorioAmbitoGenLst(Arrays.asList("E", "R", "P"))).thenReturn(mockTipoTerritorioAmbGen);
		
		List<TipoTerritorio> test = tipoTerritorioService.getTipoTerritorioAmbitoGenLst("P");
		assertEquals(3, test.size());
    }
	    
    @AfterEach
    void tearDown() {
    	//NA
    }

}
