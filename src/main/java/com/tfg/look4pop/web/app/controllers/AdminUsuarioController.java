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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg.look4pop.web.app.models.dto.UsuarioFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.entity.Role;
import com.tfg.look4pop.web.app.models.entity.Usuario;
import com.tfg.look4pop.web.app.models.service.IRoleService;
import com.tfg.look4pop.web.app.models.service.IUsuarioService;
import com.tfg.look4pop.web.app.models.service.IUtilidadesService;
import com.tfg.look4pop.web.app.validations.UsuarioFormValidador;

@Controller
@RequestMapping("admin/usuarios")
@RemoteProxy(name = "usuarioController")
public class AdminUsuarioController {

	private static final Logger LOGGER = LogManager.getLogger(AdminUsuarioController.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRoleService roleService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	@Autowired
	private UsuarioFormValidador usuarioFormValidador;
	
	// Proceso de mapear (poblar) y validar el formulario. Se hace de forma transparente
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Registramos (añadimo) nuestro validador personalizado
		binder.addValidators(usuarioFormValidador);
	}
	
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("")
	public String listarAccionesUsuarios(Model model, Locale locale) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/usuarios/acciones";
	}
	
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("/alta")
	public String crearUsuario(Model model, Locale locale) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/usuarios/alta";
	}
	
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("/modificar")
	public String modificarUsuario(Model model, Locale locale) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/usuarios/modificar";
	}
		
	// Pagina de 'Acciones sobre los usuarios'
	@GetMapping("/eliminar")
	public String eliminarUsuario(Model model, Locale locale) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/usuarios/eliminar";
	}
	
	// Formulario
	/*
	@GetMapping("/form")
	public String getUsuarioForm(Model model, Locale locale, HttpServletRequest request) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/usuarios/acciones";
	}
	*/
	
	// Formulario de alta
	@GetMapping("/alta/form")
	public String getUsuarioAltaForm(Model model, Locale locale, HttpServletRequest request) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
			
		return "admin/usuarios/alta";
	}
	
	// Formulario de modificacion
	@GetMapping("/modificar/form")
	public String getUsuarioModificarForm(Model model, Locale locale, HttpServletRequest request) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
				
		return "admin/usuarios/modificar";
	}
	
	// Formulario de eliminacion
	@GetMapping("/eliminar/form")
	public String getUsuarioEliminarForm(Model model, Locale locale, HttpServletRequest request) {
		UsuarioFormDTO usuarioForm = new UsuarioFormDTO();
		model.addAttribute("usuarioForm", usuarioForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
					
		return "admin/usuarios/eliminar";
	}
	
	// Procesamiento formulario de consulta usuario
	//@PostMapping(path = "/form", params = "alta")
	@PostMapping("/alta/form")
	public String procesarAltaUsuario(@Valid @ModelAttribute("usuarioForm") UsuarioFormDTO usuarioForm, BindingResult result, Model model, Locale locale, HttpServletRequest request, Authentication authentication) {
						
		String error = null;
		String success = null;
		String bcryptPassword = null;
			
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Alta de usuario' contiene errores");
			return "admin/usuarios/alta";
		}
		
		Usuario usuarioExist = usuarioService.getUsuarioByUsername(usuarioForm.getUsername());
		
		if (usuarioExist != null) {
			error = messageSource.getMessage("text.admin.usuarios.error.alta.1", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Alta de usuario' contiene errores");
			return "admin/usuarios/alta";
		}
	
		Usuario usuarioNew = new Usuario();
		Role roleNew = new Role();
		
		try {
			
			//Codificamos la password 
			bcryptPassword = passwordEncoder.encode(usuarioForm.getPassword());
			
			// Usuario nuevo
			usuarioNew.setUsername(usuarioForm.getUsername());
			usuarioNew.setPassword(bcryptPassword);
			usuarioNew.setNbregtro(authentication.getName());
			usuarioService.insert(usuarioNew);
			
			Usuario usuarioInserted = usuarioService.getUsuarioByUsername(usuarioForm.getUsername());
			
			// Rol asociado
			roleNew.setUserId(usuarioInserted.getId());
			roleNew.setAuthority("ROLE_ADMIN");
			roleNew.setNbregtro(authentication.getName());
			roleService.insert(roleNew);
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.usuarios.error.alta.2", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("Error al insertar en tabla el nuevo usuario / role");
			return "admin/usuarios/alta";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.usuarios.alta.ok", null, locale);
		model.addAttribute("success", success);
		utilidadesService.activarOpcionMenu(model, 4);
		LOGGER.info("Nuevo usuario registrado correctamente por " + authentication.getName());
		return "admin/usuarios/alta";	
	}
	
	// Procesamiento formulario de consulta usuario
	//@PostMapping(path = "/form", params = "modificar")
	@PostMapping("/modificar/form")
	public String procesarModificacionUsuario(@Valid @ModelAttribute("usuarioForm") UsuarioFormDTO usuarioForm, BindingResult result, Model model, Locale locale, HttpServletRequest request, Authentication authentication) {
							
		String error = null;
		String success = null;
				
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Modificacion de usuario' contiene errores");
			return "admin/usuarios/modificar";
		}
		
		Usuario usuarioExist = usuarioService.getUsuarioByUsername(usuarioForm.getUsername());
		
		if ((usuarioExist != null)
				&& (usuarioExist.getId()) != (Integer.parseInt(usuarioForm.getIdUsuarioSelect()))) {
			error = messageSource.getMessage("text.admin.usuarios.error.modificar.1", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Modificacion de usuario' contiene errores");
			return "admin/usuarios/modificar";
		}
		
		Usuario usuarioMod = new Usuario();
		
		try {
			
			// Usuario modificado
			usuarioMod.setId(Integer.parseInt(usuarioForm.getIdUsuarioSelect()));
			usuarioMod.setUsername(usuarioForm.getUsername());
			usuarioMod.setPassword(usuarioForm.getPassword());
			usuarioMod.setEnabled(Integer.parseInt(usuarioForm.getEnabled()));
			usuarioMod.setNblogact(authentication.getName());
			usuarioService.update(usuarioMod);
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.usuarios.error.modificar.2", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("Error al modificar el usuario en tabla");
			return "admin/usuarios/modificar";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.usuarios.modificar.ok", null, locale);
		model.addAttribute("success", success);
		// Carga usuarios
		model.addAttribute("selectUsuarios", usuarioService.getUsuarioLst());
		utilidadesService.activarOpcionMenu(model, 4);
		LOGGER.info("Usuario modificado correctamente por " + authentication.getName());
		return "admin/usuarios/modificar";	
	}
	
	// Procesamiento formulario de consulta usuario
	//@PostMapping(path = "/form", params = "eliminar")
	@PostMapping("/eliminar/form")
	public String procesarEliminacionUsuario(@Valid @ModelAttribute("usuarioForm") UsuarioFormDTO usuarioForm, BindingResult result, Model model, Locale locale, HttpServletRequest request, Authentication authentication) {
								
		String error = null;
		String success = null;
					
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("El formulario de 'Eliminacion de usuario' contiene errores");
			return "admin/usuarios/eliminar";
		}
		
		Usuario usuarioDel = new Usuario();
		
		try {
			
			// Usuario eliminado
			usuarioDel.setId(Integer.parseInt(usuarioForm.getIdUsuarioSelect()));
			usuarioService.delete(usuarioDel);
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.usuarios.error.eliminar", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 4);
			LOGGER.info("Error al eliminar el usuario en tabla");
			return "admin/usuarios/eliminar";
		}
					
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.usuarios.eliminar.ok", null, locale);
		model.addAttribute("success", success);
		utilidadesService.activarOpcionMenu(model, 4);
		LOGGER.info("Usuario eliminado correctamente por " + authentication.getName());			
		return "admin/usuarios/eliminar";	
	}
	
	@RemoteMethod
	public List<Usuario> getUsuarios() {
		return usuarioService.getUsuarioLst();
	}

	@RemoteMethod
	public Usuario getUsuarioById(Integer id) {
		return usuarioService.getUsuarioById(id);
	}
	
	@ModelAttribute
	public void gestionaModelos(UsuarioFormDTO usuarioForm, Model model) throws Exception {
		
		// Carga usuarios
		model.addAttribute("selectUsuarios", usuarioService.getUsuarioLst());
		
	}
			
}
