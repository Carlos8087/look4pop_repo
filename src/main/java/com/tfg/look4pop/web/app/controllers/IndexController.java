package com.tfg.look4pop.web.app.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tfg.look4pop.web.app.models.service.IUtilidadesService;

@Controller
public class IndexController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUtilidadesService utilidadesService;

	// Pagina de 'Bienvenida'
	@GetMapping({ "/", "/index" })
	public String index(Model model, Locale locale) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.index.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 1);
		return "index";
	}
	
}
