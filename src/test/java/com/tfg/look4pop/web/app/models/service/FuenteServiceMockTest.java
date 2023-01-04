package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.mapper.IFuenteMapper;

@SpringBootTest
public class FuenteServiceMockTest {
	
	@Mock
	private IFuenteMapper mockFuenteMapper;
	
	@InjectMocks // auto inject mockFuenteMapper
    private IFuenteService fuenteService = new FuenteServiceImpl();
	
	@BeforeEach
	void setUp() {
		//NA
	}	
	
	// Test getFuenteCensoByTipoLst
	@DisplayName("FuenteService -> getFuenteCensoByTipoLst")
    @Test
    void testGetFuenteCensoByTipoLst() {
		Fuente fuente1 = new Fuente("censo", "derecho", "1842", null);
		Fuente fuente2 = new Fuente("censo", "derecho", "1877", null);
		Fuente fuente3 = new Fuente("censo", "derecho", "1887", null);
		Fuente fuente4 = new Fuente("censo", "derecho", "1897", null);
		Fuente fuente5 = new Fuente("censo", "derecho", "1900", null);
		Fuente fuente6 = new Fuente("censo", "derecho", "1910", null);
		Fuente fuente7 = new Fuente("censo", "derecho", "1920", null);
		Fuente fuente8 = new Fuente("censo", "derecho", "1930", null);
		Fuente fuente9 = new Fuente("censo", "derecho", "1940", null);
		Fuente fuente10 = new Fuente("censo", "derecho", "1950", null);
		Fuente fuente11 = new Fuente("censo", "derecho", "1960", null);
		Fuente fuente12 = new Fuente("censo", "derecho", "1970", null);
		Fuente fuente13 = new Fuente("censo", "derecho", "1981", null);
		Fuente fuente14 = new Fuente("censo", "derecho", "1991", null);
		Fuente fuente15 = new Fuente("censo", "derecho", "2001", null);
		Fuente fuente16 = new Fuente("censo", "derecho", "2011", null);
		
		List<Fuente> mockFuenteCensoDerAll = new ArrayList<Fuente>();
		mockFuenteCensoDerAll.add(fuente1);
		mockFuenteCensoDerAll.add(fuente2);
		mockFuenteCensoDerAll.add(fuente3);
		mockFuenteCensoDerAll.add(fuente4);
		mockFuenteCensoDerAll.add(fuente5);
		mockFuenteCensoDerAll.add(fuente6);
		mockFuenteCensoDerAll.add(fuente7);
		mockFuenteCensoDerAll.add(fuente8);
		mockFuenteCensoDerAll.add(fuente9);
		mockFuenteCensoDerAll.add(fuente10);
		mockFuenteCensoDerAll.add(fuente11);
		mockFuenteCensoDerAll.add(fuente12);
		mockFuenteCensoDerAll.add(fuente13);
		mockFuenteCensoDerAll.add(fuente14);
		mockFuenteCensoDerAll.add(fuente15);
		mockFuenteCensoDerAll.add(fuente16);
		
		when(mockFuenteMapper.getFuenteCensoByTipoLst("derecho")).thenReturn(mockFuenteCensoDerAll);
		
		List<Fuente> test = fuenteService.getFuenteCensoByTipoLst("derecho");
		assertEquals(16, test.size());
    }
	
	// Test getFuentePadronLst
	@DisplayName("FuenteService -> getFuentePadronLst")
    @Test
    void testGetFuentePadronLst() {
		Fuente fuente1 = new Fuente("padrón", null, "1986", null);
		Fuente fuente2 = new Fuente("padrón", null, "1996", null);
		Fuente fuente3 = new Fuente("padrón", null, "1998", null);
		Fuente fuente4 = new Fuente("padrón", null, "1999", null);
		Fuente fuente5 = new Fuente("padrón", null, "2000", null);
		Fuente fuente6 = new Fuente("padrón", null, "2001", null);
		Fuente fuente7 = new Fuente("padrón", null, "2002", null);
		Fuente fuente8 = new Fuente("padrón", null, "2003", null);
		Fuente fuente9 = new Fuente("padrón", null, "2004", null);
		Fuente fuente10 = new Fuente("padrón", null, "2005", null);
		Fuente fuente11 = new Fuente("padrón", null, "2006", null);
		Fuente fuente12 = new Fuente("padrón", null, "2007", null);
		Fuente fuente13 = new Fuente("padrón", null, "2008", null);
		Fuente fuente14 = new Fuente("padrón", null, "2009", null);
		Fuente fuente15 = new Fuente("padrón", null, "2010", null);
		Fuente fuente16 = new Fuente("padrón", null, "2011", null);
		Fuente fuente17 = new Fuente("padrón", null, "2012", null);
		Fuente fuente18 = new Fuente("padrón", null, "2013", null);
		Fuente fuente19 = new Fuente("padrón", null, "2014", null);
		Fuente fuente20 = new Fuente("padrón", null, "2015", null);
		Fuente fuente21 = new Fuente("padrón", null, "2016", null);
		Fuente fuente22 = new Fuente("padrón", null, "2017", null);
		Fuente fuente23 = new Fuente("padrón", null, "2018", null);
		
		List<Fuente> mockFuentePadronAll = new ArrayList<Fuente>();
		mockFuentePadronAll.add(fuente1);
		mockFuentePadronAll.add(fuente2);
		mockFuentePadronAll.add(fuente3);
		mockFuentePadronAll.add(fuente4);
		mockFuentePadronAll.add(fuente5);
		mockFuentePadronAll.add(fuente6);
		mockFuentePadronAll.add(fuente7);
		mockFuentePadronAll.add(fuente8);
		mockFuentePadronAll.add(fuente9);
		mockFuentePadronAll.add(fuente10);
		mockFuentePadronAll.add(fuente11);
		mockFuentePadronAll.add(fuente12);
		mockFuentePadronAll.add(fuente13);
		mockFuentePadronAll.add(fuente14);
		mockFuentePadronAll.add(fuente15);
		mockFuentePadronAll.add(fuente16);
		mockFuentePadronAll.add(fuente17);
		mockFuentePadronAll.add(fuente18);
		mockFuentePadronAll.add(fuente19);
		mockFuentePadronAll.add(fuente20);
		mockFuentePadronAll.add(fuente21);
		mockFuentePadronAll.add(fuente22);
		mockFuentePadronAll.add(fuente23);
		
		when(mockFuenteMapper.getFuentePadronLst()).thenReturn(mockFuentePadronAll);
		
		List<Fuente> test = fuenteService.getFuentePadronLst();
		assertEquals(23, test.size());
    }
	
	// Test getAnioByTpFuente
	@DisplayName("FuenteService -> getAnioByTpFuente")
    @Test
    void testGetAnioByTpFuente() {
		Fuente mockFuenteCenHec1970 = new Fuente("censo", "hecho", "1970", null);
		FuenteFormDTO testFuenteForm = new FuenteFormDTO();
		testFuenteForm.setTipo("censo");
		testFuenteForm.setSubtipo("hecho");
		testFuenteForm.setAnioC("1970");
		
		when(mockFuenteMapper.getAnioByTpFuente(testFuenteForm)).thenReturn(mockFuenteCenHec1970);
		
		Fuente test = fuenteService.getAnioByTpFuente(testFuenteForm);
		assertEquals("censo", test.getTipo());
		assertEquals("hecho", test.getSubtipo());
		assertEquals("1970", test.getAnio());
    }
	
	// Test insert
	@DisplayName("FuenteService -> insert")
    @Test
    void testInsert() {
		Fuente fuente = new Fuente("padrón", null, "2050", null);
		fuenteService.insert(fuente);
		verify(mockFuenteMapper, times(1)).insert(fuente);
    }
	
	// Test delete
	@DisplayName("FuenteService -> delete")
    @Test
    void testDelete() {
		Fuente fuente = new Fuente("padrón", null, "2050", null);
		fuenteService.delete(fuente);
		verify(mockFuenteMapper, times(1)).delete(fuente);
    }
	
	@AfterEach
    void tearDown() {
    	//NA
    }

}
