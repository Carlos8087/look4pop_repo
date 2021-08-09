package com.tfg.look4pop.web.app.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.look4pop.web.app.models.service.IUtilidadesService;

@Controller
public class LoginController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	@GetMapping("/login") // Mismo nombre que usa el interceptor de Spring a la hora de realizar todo el proceso de autenticacion
	public String login(@RequestParam(value="error", required=false) String error, // 'error' -> Parametro enviado desde el formulario, de forma automatica, aunque no siempre se envia (solo cuando hay errores de validacion)
			@RequestParam(value="logout", required=false) String logout, // 'logout' -> Parametro enviado desde el formulario, de forma automatica, al hacer un logout 
			Model model, Locale locale) {
	
		if (error != null) {
			model.addAttribute("error", messageSource.getMessage("text.flash.login.credenciales.error", null, locale));
		}
		
		if (logout != null) {
			model.addAttribute("success", messageSource.getMessage("text.flash.login.credenciales.logout.ok", null, locale));
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.login.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "login";
	}

}
