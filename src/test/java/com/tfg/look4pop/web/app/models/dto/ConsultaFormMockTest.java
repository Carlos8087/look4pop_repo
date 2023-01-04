package com.tfg.look4pop.web.app.models.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConsultaFormMockTest {
	
	@BeforeEach
	void setUp() {
		
	}
	
	@DisplayName("ConsultaFormDTO -> getters y setters")
    @Test
    void testGettersYSetters() {
		ConsultaFormDTO test = new ConsultaFormDTO();
		
		test.setNivel("P");
		test.setAmbitoGen("R");
		test.setAmbitosPar(new String[] {"11", "18"});
		test.setTpsFuente(new String[] {"padron"});
		test.setAnios(new String[] {"p1999", "p2000"});
		test.setCensoDerAnios(new String[0]);
		test.setCensoHecAnios(new String[0]);
		test.setPadronAnios(new String[] {"1999", "2000"});
		test.setIdsTerritorioNivel(new Integer[] {41, 43, 50, 52, 53, 55, 57, 58, 60, 62, 68, 80, 84});
		test.setNumElementAct(0);
		test.setNumMaxRegs(1000);
		test.setNumTotRegs(null);
		
		assertEquals("P", test.getNivel());
		assertEquals("R", test.getAmbitoGen());
		assertEquals(2, test.getAmbitosPar().length);
		assertEquals(1, test.getTpsFuente().length);
		assertEquals(2, test.getAnios().length);
		assertEquals(0, test.getCensoDerAnios().length);
		assertEquals(0, test.getCensoHecAnios().length);
		assertEquals(2, test.getPadronAnios().length);
		assertEquals(13, test.getIdsTerritorioNivel().length);
		assertEquals(0, test.getNumElementAct());
		assertEquals(1000, test.getNumMaxRegs());
		assertNull(test.getNumTotRegs());
    }
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
