package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.entity.Territorio;
import com.tfg.look4pop.web.app.models.mapper.ITerritorioMapper;

@SpringBootTest
public class TerritorioServiceMockTest {
	
	@Mock
	private ITerritorioMapper mockTerritorioMapper;
	
	@InjectMocks // auto inject mockTerritorioMapper
    private ITerritorioService territorioService = new TerritorioServiceImpl();
	   
	@BeforeEach
	void setUp() {
		//NA
	}
	
	// Test getTipoTerritorioById
	@DisplayName("TerritorioService -> getTerritorioById")
    @Test
    void testGetTerritorioById() {
		Territorio mockTerritorioBarce = new Territorio();
		mockTerritorioBarce.setIdTerritorio(47);
		mockTerritorioBarce.setNombreActual("Barcelona");
		
		when(mockTerritorioMapper.getTerritorioById("47")).thenReturn(mockTerritorioBarce);
		
		Territorio test = territorioService.getTerritorioById("47");
		assertEquals("Barcelona", test.getNombreActual());
    }
	
	// Test getTerritorioIdByNivelAndAmbitoGenLst
	@DisplayName("TerritorioService -> getTerritorioIdByNivelAndAmbitoGenLst")
    @Test
    void testGetTerritorioIdByNivelAndAmbitoGenLst() {
		List<String> mockRegionesId = Arrays.asList("26", "18", "20", "11", "17", "21", "14", "19", "22", 
				"12", "27", "23", "24", "25", "13", "15", "16", "28", "29");
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("nivel", "P");
		testMap.put("ambitoGen", "R");
		
		when(mockTerritorioMapper.getTerritorioIdByNivelAndAmbitoGenLst(testMap)).thenReturn(mockRegionesId);
		
		List<String> test = territorioService.getTerritorioIdByNivelAndAmbitoGenLst(testMap);
		assertEquals(19, test.size());
    }
    
	// Test getTerritorioAmbitoParLst
	@DisplayName("TerritorioService -> getTerritorioAmbitoParLst")
    @Test
    void testGetTerritorioAmbitoParLst() {
		List<String> mockRegionesId = Arrays.asList("26", "18", "20", "11", "17", "21", "14", "19", "22", 
				"12", "27", "23", "24", "25", "13", "15", "16", "28", "29");
		List<Territorio> mockRegiones = new ArrayList<Territorio>();
		inicializarRegiones(mockRegiones);
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("nivel", "P");
		testMap.put("ambitoGen", "R");
		
		when(mockTerritorioMapper.getTerritorioIdByNivelAndAmbitoGenLst(testMap)).thenReturn(mockRegionesId);
		when(mockTerritorioMapper.getTerritorioAmbitoParLst(mockRegionesId)).thenReturn(mockRegiones);
		
		List<Territorio> test = territorioService.getTerritorioAmbitoParLst("P", "R");
		assertEquals(19, test.size());
    }
	
	// Test getTerritorioIdIncludedInAmbitoGenLst
	@DisplayName("TerritorioService -> getTerritorioIdIncludedInAmbitoGenLst")
    @Test
    void testGetTerritorioIdIncludedInAmbitoGenLst() {
		Integer[] mockProvinciasId = new Integer[] {41, 52, 55, 58, 84};		
		ConsultaFormDTO mockConsultaForm = new ConsultaFormDTO();
		mockConsultaForm.setNivel("P");
		mockConsultaForm.setAmbitoGen("R");
		mockConsultaForm.setAmbitosPar(new String[] {"18"}); // Castilla-La Mancha
		
		when(mockTerritorioMapper.getTerritorioIdIncludedInAmbitoGenLst(mockConsultaForm)).thenReturn(mockProvinciasId);
		
		Integer[] test = territorioService.getTerritorioIdIncludedInAmbitoGenLst(mockConsultaForm);
		assertEquals(5, test.length);
    }
	
	@AfterEach
	void tearDown() {
		//NA
	}
	
	private void inicializarRegiones(List<Territorio> mockRegiones) {
		Territorio territorio1 = new Territorio();
		
		territorio1.setIdTerritorio(11);
		territorio1.setNombreActual("Andalucía");
		mockRegiones.add(territorio1);
		
		Territorio territorio2 = new Territorio();
		territorio2.setIdTerritorio(12);
		territorio2.setNombreActual("Aragón");
		mockRegiones.add(territorio2);
		
		Territorio territorio3 = new Territorio();
		territorio3.setIdTerritorio(13);
		territorio3.setNombreActual("Asturias (Principado de)");
		mockRegiones.add(territorio3);
		
		Territorio territorio4 = new Territorio();
		territorio4.setIdTerritorio(14);
		territorio4.setNombreActual("Balears (Illes)");
		mockRegiones.add(territorio4);
		
		Territorio territorio5 = new Territorio();
		territorio5.setIdTerritorio(15);
		territorio5.setNombreActual("Canarias");
		mockRegiones.add(territorio5);
		
		Territorio territorio6 = new Territorio();
		territorio6.setIdTerritorio(16);
		territorio6.setNombreActual("Cantabria");
		mockRegiones.add(territorio6);
		
		Territorio territorio7 = new Territorio();
		territorio7.setIdTerritorio(17);
		territorio7.setNombreActual("Castilla y León");
		mockRegiones.add(territorio7);
		
		Territorio territorio8 = new Territorio();
		territorio8.setIdTerritorio(18);
		territorio8.setNombreActual("Castilla-La Mancha");
		mockRegiones.add(territorio8);
		
		Territorio territorio9 = new Territorio();
		territorio9.setIdTerritorio(19);
		territorio9.setNombreActual("Cataluña");
		mockRegiones.add(territorio9);
		
		Territorio territorio10 = new Territorio();
		territorio10.setIdTerritorio(20);
		territorio10.setNombreActual("Comunidad Valenciana");
		mockRegiones.add(territorio10);
		
		Territorio territorio11 = new Territorio();
		territorio11.setIdTerritorio(21);
		territorio11.setNombreActual("Extremadura");
		mockRegiones.add(territorio11);
		
		Territorio territorio12= new Territorio();
		territorio12.setIdTerritorio(22);
		territorio12.setNombreActual("Galicia");
		mockRegiones.add(territorio12);
		
		Territorio territorio13 = new Territorio();
		territorio13.setIdTerritorio(23);
		territorio13.setNombreActual("Madrid (Comunidad de)");
		mockRegiones.add(territorio13);
		
		Territorio territorio14 = new Territorio();
		territorio14.setIdTerritorio(24);
		territorio14.setNombreActual("Murcia (Región de)");
		mockRegiones.add(territorio14);
		
		Territorio territorio15 = new Territorio();
		territorio15.setIdTerritorio(25);
		territorio15.setNombreActual("Navarra (Comunidad Foral de)");
		mockRegiones.add(territorio15);
		
		Territorio territorio16 = new Territorio();
		territorio16.setIdTerritorio(26);
		territorio16.setNombreActual("País Vasco");
		mockRegiones.add(territorio16);
		
		Territorio territorio17 = new Territorio();
		territorio17.setIdTerritorio(27);
		territorio17.setNombreActual("Rioja (La)");
		mockRegiones.add(territorio17);
		
		Territorio territorio18 = new Territorio();
		territorio18.setIdTerritorio(28);
		territorio18.setNombreActual("Ceuta (Ciudad de)");
		mockRegiones.add(territorio18);
		
		Territorio territorio19 = new Territorio();
		territorio19.setIdTerritorio(29);
		territorio19.setNombreActual("Melilla (Ciudad de)");
		mockRegiones.add(territorio19);
	}

}
