package com.tfg.look4pop.web.app.models.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityMockTest {
	
	@BeforeEach
	void setUp() {
		//NA
	}
	
	// Test TipoTerritorio entity
	@DisplayName("TipoTerritorio entity")
    @Test
    void testTipoTerritorio() {
		TipoTerritorio test = new TipoTerritorio();
		
		test.setIdTt("P");
		test.setNombre("Provincia");
		test.setComentarios(null);
		test.setPerteneceA("E,R,P");
		
		assertEquals("P", test.getIdTt());
		assertEquals("Provincia", test.getNombre());
		assertNull(test.getComentarios());
		assertEquals("E,R,P", test.getPerteneceA());
		
		TipoTerritorio testConstructorFields = new TipoTerritorio("R", "Región", "Comunidad Autónoma o Ciudad Autonóma", "E,R");
		
		assertEquals("R", testConstructorFields.getIdTt());
		assertEquals("Región", testConstructorFields.getNombre());
		assertEquals("Comunidad Autónoma o Ciudad Autonóma", testConstructorFields.getComentarios());
		assertEquals("E,R", testConstructorFields.getPerteneceA());
    }
	
	// Test Territorio entity
	@DisplayName("Territorio entity")
    @Test
    void testTerritorio() {
		Territorio test = new Territorio();
		
		test.setIdTerritorio(84);
		test.setNombreActual("Toledo");
		test.setNombreCortoActual("Toledo");
		test.setTipoTerritorio("P");
		test.setCodigoOficial("45");
		test.setE(1);
		test.setR(18);
		test.setP(84);
		test.setM(null);
		test.setMv(null);
		test.setC(null);
		test.setAu(null);
		test.setAuv(null);
		test.setI(null);
		test.setZc(null);
		test.setAc(null);
		test.setDu(21000);
		test.setPj(null);
		test.setZf(null);
		test.setKm2(15370.24);
		test.setEste(507984.0);
		test.setOeste(294348.0);
		test.setNorte(4464213.0);
		test.setSur(4346021.0);
		test.setX(412492);
		test.setY(4412611);
		test.setCapital(7027);
		test.setActivo((byte) 1);
		
		assertEquals(84, test.getIdTerritorio());
		assertEquals("Toledo", test.getNombreActual());
		assertEquals("Toledo", test.getNombreCortoActual());
		assertEquals("P", test.getTipoTerritorio());
		assertEquals("45", test.getCodigoOficial());
		assertEquals(1, test.getE());
		assertEquals(18, test.getR());
		assertEquals(84, test.getP());
		assertNull(test.getM());
		assertNull(test.getMv());
		assertNull(test.getC());
		assertNull(test.getAu());
		assertNull(test.getAuv());
		assertNull(test.getI());
		assertNull(test.getZc());
		assertNull(test.getAc());
		assertEquals(21000, test.getDu());
		assertNull(test.getPj());
		assertNull(test.getZf());
		assertEquals(15370.24, test.getKm2());
		assertEquals(507984.0, test.getEste());
		assertEquals(294348.0, test.getOeste());
		assertEquals(4464213.0, test.getNorte());
		assertEquals(4346021.0, test.getSur());
		assertEquals(412492, test.getX());
		assertEquals(4412611, test.getY());
		assertEquals(7027, test.getCapital());
		assertEquals((byte) 1, test.getActivo());
		
		Territorio testConstructorFields = new Territorio(18, "Castilla-La Mancha", "Castilla-La Mancha", "R", "08",
				1, 18, null, null, null, null, null, null, null, null, null, null, null, null,
				79305.2199999998, 681364.0, 294348.0, 4575341.0, 4208707.0, 412492, 4412611, 7027, (byte) 1);
		
		assertEquals(18, testConstructorFields.getIdTerritorio());
		assertEquals("Castilla-La Mancha", testConstructorFields.getNombreActual());
		assertEquals("Castilla-La Mancha", testConstructorFields.getNombreCortoActual());
		assertEquals("R", testConstructorFields.getTipoTerritorio());
		assertEquals("08", testConstructorFields.getCodigoOficial());
		assertEquals(1, testConstructorFields.getE());
		assertEquals(18, testConstructorFields.getR());
		assertNull(testConstructorFields.getP());
		assertNull(testConstructorFields.getM());
		assertNull(testConstructorFields.getMv());
		assertNull(testConstructorFields.getC());
		assertNull(testConstructorFields.getAu());
		assertNull(testConstructorFields.getAuv());
		assertNull(testConstructorFields.getI());
		assertNull(testConstructorFields.getZc());
		assertNull(testConstructorFields.getAc());
		assertNull(testConstructorFields.getDu());
		assertNull(testConstructorFields.getPj());
		assertNull(testConstructorFields.getZf());
		assertEquals(79305.2199999998, testConstructorFields.getKm2());
		assertEquals(681364.0, testConstructorFields.getEste());
		assertEquals(294348.0, testConstructorFields.getOeste());
		assertEquals(4575341.0, testConstructorFields.getNorte());
		assertEquals(4208707.0, testConstructorFields.getSur());
		assertEquals(412492, testConstructorFields.getX());
		assertEquals(4412611, testConstructorFields.getY());
		assertEquals(7027, testConstructorFields.getCapital());
		assertEquals((byte) 1, testConstructorFields.getActivo());
    }
	
	// Test Fuente entity
	@DisplayName("Fuente entity")
    @Test
    void testFuente() {
		Fuente test = new Fuente();
		
		test.setTipo("censo");
		test.setSubtipo("derecho");
		test.setAnio("1842");
		test.setProcedenciaDatos(null);
		
		assertEquals("censo", test.getTipo());
		assertEquals("derecho", test.getSubtipo());
		assertEquals("1842", test.getAnio());
		assertNull(test.getProcedenciaDatos());
		
		Fuente testConstructorFields = new Fuente("padrón", null, "2000", null);
		
		assertEquals("padrón", testConstructorFields.getTipo());
		assertNull(testConstructorFields.getSubtipo());
		assertEquals("2000", testConstructorFields.getAnio());
		assertNull(testConstructorFields.getProcedenciaDatos());
    }
	
	// Test Usuario entity
	@DisplayName("Usuario entity")
    @Test
    void testUsuario() throws ParseException {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = formateador.parse("01/01/2022 15:00:00");
		Timestamp ts = new Timestamp(date.getTime());
		
		Usuario test = new Usuario();
		
		test.setId(50);
		test.setUsername("admin50");
		test.setPassword("$2a$10$LjNJg/qPh80RrsgOBJjyZuaH06HFAYPVazRgcpDq2xMcF7Xd8HrJC");
		test.setEnabled(1);
		test.setNbregtro("admin99");
		test.setFhregtro(ts);
		test.setNblogact("admin99");
		test.setFhultact(ts);
		
		assertEquals(50, test.getId());
		assertEquals("admin50", test.getUsername());
		assertEquals("$2a$10$LjNJg/qPh80RrsgOBJjyZuaH06HFAYPVazRgcpDq2xMcF7Xd8HrJC", test.getPassword());
		assertEquals(1, test.getEnabled());
		assertEquals("admin99", test.getNbregtro());
		assertEquals("01/01/2022 15:00:00", formateador.format(test.getFhregtro()));
		assertEquals("admin99", test.getNblogact());
		assertEquals("01/01/2022 15:00:00", formateador.format(test.getFhultact()));
		
		Usuario testConstructorFields = new Usuario(51, "admin51", "$2a$10$LjNJg/qPh80RrsgOBJjyZuaH06HFAYPVazRgcpDq2xMcF7Xd8HrJC", 1,
				"admin99", ts, "admin99", ts);
		
		assertEquals(51, testConstructorFields.getId());
		assertEquals("admin51", testConstructorFields.getUsername());
		assertEquals("$2a$10$LjNJg/qPh80RrsgOBJjyZuaH06HFAYPVazRgcpDq2xMcF7Xd8HrJC", testConstructorFields.getPassword());
		assertEquals(1, testConstructorFields.getEnabled());
		assertEquals("admin99", testConstructorFields.getNbregtro());
		assertEquals("01/01/2022 15:00:00", formateador.format(testConstructorFields.getFhregtro()));
		assertEquals("admin99", testConstructorFields.getNblogact());
		assertEquals("01/01/2022 15:00:00", formateador.format(testConstructorFields.getFhultact()));
    }
	
	// Test Role entity
	@DisplayName("Role entity")
    @Test
    void testRole() throws ParseException {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = formateador.parse("15/01/2022 16:00:00");
		Timestamp ts = new Timestamp(date.getTime());
		
		Role test = new Role();
		
		test.setId(20);
		test.setUserId(50);
		test.setAuthority("ROLE_ADMIN");
		test.setNbregtro("admin99");
		test.setFhregtro(ts);
		test.setNblogact("admin99");
		test.setFhultact(ts);
		
		assertEquals(20, test.getId());
		assertEquals(50, test.getUserId());
		assertEquals("ROLE_ADMIN", test.getAuthority());
		assertEquals("admin99", test.getNbregtro());
		assertEquals("15/01/2022 16:00:00", formateador.format(test.getFhregtro()));
		assertEquals("admin99", test.getNblogact());
		assertEquals("15/01/2022 16:00:00", formateador.format(test.getFhultact()));
		
		Role testConstructorFields = new Role(21, 51, "ROLE_ADMIN", "admin99", ts, "admin99", ts);
		
		assertEquals(21, testConstructorFields.getId());
		assertEquals(51, testConstructorFields.getUserId());
		assertEquals("ROLE_ADMIN", testConstructorFields.getAuthority());
		assertEquals("admin99", testConstructorFields.getNbregtro());
		assertEquals("15/01/2022 16:00:00", formateador.format(testConstructorFields.getFhregtro()));
		assertEquals("admin99", testConstructorFields.getNblogact());
		assertEquals("15/01/2022 16:00:00", formateador.format(testConstructorFields.getFhultact()));
    }
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
