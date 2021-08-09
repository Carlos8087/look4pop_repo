package com.tfg.look4pop.web.app.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.service.IFuenteService;
import com.tfg.look4pop.web.app.models.service.IUtilidadesService;
import com.tfg.look4pop.web.app.validations.FuenteFormValidador;

@Controller
@RequestMapping("admin/fuentes")
@RemoteProxy(name = "fuenteController")
public class AdminFuenteController {

	private static final Logger LOGGER = LogManager.getLogger(AdminFuenteController.class);
	
	@Autowired
	private IFuenteService fuenteService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	@Autowired
	private FuenteFormValidador fuenteFormValidador;
	
	// Proceso de mapear (poblar) y validar el formulario. Se hace de forma transparente
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Registramos (añadimo) nuestro validador personalizado
		binder.addValidators(fuenteFormValidador);
	}
	
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("")
	public String listarAccionesFuentes(Model model, Locale locale) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/fuentes/acciones";
	}
	
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("/alta")
	public String crearFuente(Model model, Locale locale) {
		FuenteFormDTO fuenteForm = new FuenteFormDTO();
		model.addAttribute("fuenteForm", fuenteForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/fuentes/alta";
	}
		
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("/eliminar")
	public String eliminarFuente(Model model, Locale locale) {
		FuenteFormDTO fuenteForm = new FuenteFormDTO();
		model.addAttribute("fuenteForm", fuenteForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/fuentes/eliminar";
	}
	
	// Formulario de consulta
	/*
	@GetMapping("/form")
	public String getFuenteForm(Model model, Locale locale, HttpServletRequest request) {
		FuenteFormDTO fuenteForm = new FuenteFormDTO();
		model.addAttribute("fuenteForm", fuenteForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
			
		return "admin/fuentes/acciones";
	}
	*/
	
	// Formulario de alta
	@GetMapping("/alta/form")
	public String getFuenteAltaForm(Model model, Locale locale, HttpServletRequest request) {
		FuenteFormDTO fuenteForm = new FuenteFormDTO();
		model.addAttribute("fuenteForm", fuenteForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
				
		return "admin/fuentes/alta";
	}
	
	// Formulario de eliminacion
	@GetMapping("/eliminar/form")
	public String getFuenteEliminarForm(Model model, Locale locale, HttpServletRequest request) {
		FuenteFormDTO fuenteForm = new FuenteFormDTO();
		model.addAttribute("fuenteForm", fuenteForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
					
		return "admin/fuentes/eliminar";
	}
	
	// Procesamiento formulario de consulta usuario
	//@PostMapping(path = "/form", params = "alta")
	@PostMapping("/alta/form")
	public String procesarAltaFuente(@Valid @ModelAttribute("fuenteForm") FuenteFormDTO fuenteForm, BindingResult result, Model model, Locale locale, HttpServletRequest request, Authentication authentication) {
						
		String error = null;
		String success = null;
			
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Alta de fuente' contiene errores");
			return "admin/fuentes/alta";
		}
		
		Fuente fuenteExist = fuenteService.getAnioByTpFuente(fuenteForm);
		
		if (fuenteExist != null) {
			error = messageSource.getMessage("text.admin.fuentes.error.alta.1", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Alta de fuente' contiene errores");
			return "admin/fuentes/alta";
		}
	
		Fuente fuenteNew = new Fuente();
		
		try {
			
			// Fuente nueva
			if ("censo".equals(fuenteForm.getTipo())) {
				fuenteNew.setTipo(fuenteForm.getTipo());
				fuenteNew.setSubtipo(fuenteForm.getSubtipo());
				fuenteNew.setAnio(fuenteForm.getAnioC());
			}
			if ("padron".equals(fuenteForm.getTipo())) {
				fuenteNew.setTipo(fuenteForm.getTipo());
				fuenteNew.setSubtipo("");
				fuenteNew.setAnio(fuenteForm.getAnioP());
			}
			fuenteService.insert(fuenteNew);
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.fuentes.error.alta.2", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("Error al insertar en tabla la nueva fuente");
			return "admin/fuentes/alta";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.fuentes.alta.ok", null, locale);
		model.addAttribute("success", success);
		utilidadesService.activarOpcionMenu(model, 4);
		LOGGER.info("Nueva fuente registrada correctamente " + authentication.getName());
		return "admin/fuentes/alta";	
	}
	
	// Procesamiento formulario de consulta usuario
	//@PostMapping(path = "/form", params = "eliminar")
	@PostMapping("/eliminar/form")
	public String procesarEliminacionFuente(@Valid @ModelAttribute("fuenteForm") FuenteFormDTO fuenteForm, BindingResult result, Model model, Locale locale, HttpServletRequest request, Authentication authentication) {
								
		String error = null;
		String success = null;
					
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Eliminacion de fuente' contiene errores");
			return "admin/fuentes/eliminar";
		}
					
		Fuente fuenteDel = new Fuente();
		
		try {
			
			// Fuente eliminada
			if ("censo".equals(fuenteForm.getTipo())) {
				fuenteDel.setTipo(fuenteForm.getTipo());
				fuenteDel.setSubtipo(fuenteForm.getSubtipo());
				fuenteDel.setAnio(fuenteForm.getAnioC());
			}
			if ("padron".equals(fuenteForm.getTipo())) {
				fuenteDel.setTipo(fuenteForm.getTipo());
				fuenteDel.setAnio(fuenteForm.getAnioP());
			}
			fuenteService.delete(fuenteDel);
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.fuentes.error.eliminar", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("Error al eliminar la fuente en tabla");
			return "admin/fuentes/eliminar";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.fuentes.eliminar.ok", null, locale);
		model.addAttribute("success", success);
		utilidadesService.activarOpcionMenu(model, 4);
		LOGGER.info("Fuente eliminada correctamente por " + authentication.getName());		
		return "admin/fuentes/eliminar";	
	}
	
	/*
	@RemoteMethod
	public List<Fuente> getFuenteCensoByTpCensoLst(String tpCenso) {
		return fuenteService.getFuenteCensoByTpCensoLst(tpCenso);
	}
	*/
	
	@RemoteMethod
	public List<Fuente> getFuenteCensoByTipoLst(String tpCenso) {
		return fuenteService.getFuenteCensoByTipoLst(tpCenso);
	}
	
	@RemoteMethod
	public List<Fuente> getFuentePadronLst() {
		return fuenteService.getFuentePadronLst();
	}
			
}
