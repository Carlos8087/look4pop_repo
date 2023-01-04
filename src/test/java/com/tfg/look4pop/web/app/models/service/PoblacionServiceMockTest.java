package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;
import com.tfg.look4pop.web.app.models.mapper.IPoblacionMapper;
import com.tfg.look4pop.web.app.models.mapper.ITerritorioMapper;

@SpringBootTest
public class PoblacionServiceMockTest {
	
	@Mock
	private IPoblacionMapper mockPoblacionMapper;
	
	@Mock
	private ITerritorioMapper mockTerritorioMapper;
	
	@InjectMocks // auto inject mockPoblacionMapper and mockTerritorioMapper
    private IPoblacionService poblacionService = new PoblacionServiceImpl();
	   
	@BeforeEach
	void setUp() {
		//NA
	}
	
	// Test getPoblacionByMunicipioLst
	@DisplayName("PoblacionService -> getPoblacionByMunicipioLst")
    @Test
    void testGetPoblacionByMunicipioLst() {
		ConsultaFormDTO testConsultaForm = new ConsultaFormDTO();
		testConsultaForm.setNivel("M");
		testConsultaForm.setAmbitoGen("M");
		testConsultaForm.setAmbitosPar(new String[] {"7051"});
		testConsultaForm.setTpsFuente(new String[] {"censoderecho"});
		testConsultaForm.setAnios(new String[] {"d1950"});
		testConsultaForm.setCensoDerAnios(new String[] {"1950"});
		testConsultaForm.setCensoHecAnios(new String[0]);
		testConsultaForm.setPadronAnios(new String[0]);
		testConsultaForm.setIdsTerritorioNivel(null);
		testConsultaForm.setNumElementAct(0);
		testConsultaForm.setNumMaxRegs(1000);
		testConsultaForm.setNumTotRegs(null);
		
		List<PoblacionDataDTO> mockPoblacion = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionVilla = new PoblacionDataDTO();
		poblacionVilla.setAnio("1950");
		poblacionVilla.setCodigoOficial("45193");
		poblacionVilla.setNombre("Villanueva de Bogas");
		poblacionVilla.setPoblacion("1254");
		poblacionVilla.setTipoFuente("censo derecho");
		poblacionVilla.setTipoTerritorio("Municipio");
		mockPoblacion.add(poblacionVilla);
		
		when(mockPoblacionMapper.getPoblacionByMunicipioLst(testConsultaForm)).thenReturn(mockPoblacion);
		
		List<PoblacionDataDTO> test = poblacionService.getPoblacionByMunicipioLst(testConsultaForm);
		assertEquals(1, test.size());
    }
	
	// Test getPoblacionLst
	@DisplayName("PoblacionService -> getPoblacionLst")
    @Test
    void testGetPoblacionLst() {
		Integer[] mockProvinciasId = new Integer[] {41, 52, 55, 58, 84};
		ConsultaFormDTO testConsultaForm = new ConsultaFormDTO();
		testConsultaForm.setNivel("P");
		testConsultaForm.setAmbitoGen("R");
		testConsultaForm.setAmbitosPar(new String[] {"18"});
		testConsultaForm.setTpsFuente(new String[] {"padron"});
		testConsultaForm.setAnios(new String[] {"p1998"});
		testConsultaForm.setCensoDerAnios(new String[0]);
		testConsultaForm.setCensoHecAnios(new String[0]);
		testConsultaForm.setPadronAnios(new String[] {"1998"});
		testConsultaForm.setIdsTerritorioNivel(null);
		testConsultaForm.setNumElementAct(0);
		testConsultaForm.setNumMaxRegs(1000);
		testConsultaForm.setNumTotRegs(null);
		
		when(mockTerritorioMapper.getTerritorioIdIncludedInAmbitoGenLst(testConsultaForm)).thenReturn(mockProvinciasId);
		testConsultaForm.setIdsTerritorioNivel(mockProvinciasId);
		
		List<PoblacionDataDTO> mockPoblacion = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionAlba = new PoblacionDataDTO();
		poblacionAlba.setAnio("1998");
		poblacionAlba.setCodigoOficial("02");
		poblacionAlba.setNombre("Albacete");
		poblacionAlba.setPoblacion("358597");
		poblacionAlba.setTipoFuente("padrón");
		poblacionAlba.setTipoTerritorio("Provincia");
		mockPoblacion.add(poblacionAlba);
		
		PoblacionDataDTO poblacionCiud = new PoblacionDataDTO();
		poblacionCiud.setAnio("1998");
		poblacionCiud.setCodigoOficial("13");
		poblacionCiud.setNombre("Ciudad Real");
		poblacionCiud.setPoblacion("479474");
		poblacionCiud.setTipoFuente("padrón");
		poblacionCiud.setTipoTerritorio("Provincia");
		mockPoblacion.add(poblacionCiud);
		
		PoblacionDataDTO poblacionCuen = new PoblacionDataDTO();
		poblacionCuen.setAnio("1998");
		poblacionCuen.setCodigoOficial("16");
		poblacionCuen.setNombre("Cuenca");
		poblacionCuen.setPoblacion("199086");
		poblacionCuen.setTipoFuente("padrón");
		poblacionCuen.setTipoTerritorio("Provincia");
		mockPoblacion.add(poblacionCuen);
		
		PoblacionDataDTO poblacionGuad = new PoblacionDataDTO();
		poblacionGuad.setAnio("1998");
		poblacionGuad.setCodigoOficial("19");
		poblacionGuad.setNombre("Guadalajara");
		poblacionGuad.setPoblacion("159331");
		poblacionGuad.setTipoFuente("padrón");
		poblacionGuad.setTipoTerritorio("Provincia");
		mockPoblacion.add(poblacionGuad);
		
		PoblacionDataDTO poblacionTole = new PoblacionDataDTO();
		poblacionTole.setAnio("1998");
		poblacionTole.setCodigoOficial("45");
		poblacionTole.setNombre("Toledo");
		poblacionTole.setPoblacion("519664");
		poblacionTole.setTipoFuente("padrón");
		poblacionTole.setTipoTerritorio("Provincia");
		mockPoblacion.add(poblacionTole);
		
		when(mockPoblacionMapper.getPoblacionLst(testConsultaForm)).thenReturn(mockPoblacion);
		
		List<PoblacionDataDTO> test = poblacionService.getPoblacionLst(testConsultaForm);
		assertEquals(5, test.size());
    }
	
	@AfterEach
    void tearDown() {
    	//NA
    }

}
