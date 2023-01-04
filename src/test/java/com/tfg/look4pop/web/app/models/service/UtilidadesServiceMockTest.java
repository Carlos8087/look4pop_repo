package com.tfg.look4pop.web.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

@SpringBootTest
public class UtilidadesServiceMockTest {
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	@BeforeEach
	void setUp() {
		//NA
	}	
	
	// Test activarOpcionMenu
	@DisplayName("UtilidadesService -> activarOpcionMenu")
    @Test
    void testActivarOpcionMenu() {
		Model model = new BindingAwareModelMap();
		utilidadesService.activarOpcionMenu(model, 1);
		assertEquals("nav-item active", model.getAttribute("clsInicio"));
		
		utilidadesService.activarOpcionMenu(model, 2);
		assertEquals("dropdown-item active", model.getAttribute("clsConsuForm"));
		assertEquals("nav-item dropdown active", model.getAttribute("clsConsultas"));
		
		utilidadesService.activarOpcionMenu(model, 3);
		assertEquals("dropdown-item active", model.getAttribute("clsConsuFile"));
		assertEquals("nav-item dropdown active", model.getAttribute("clsConsultas"));
		
		utilidadesService.activarOpcionMenu(model, 4);
		assertEquals("nav-item active", model.getAttribute("clsAdmin"));
    }
	
	@AfterEach
    void tearDown() {
    	//NA
    }

}
