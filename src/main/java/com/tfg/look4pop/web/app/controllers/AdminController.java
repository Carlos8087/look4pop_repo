package com.tfg.look4pop.web.app.controllers;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.look4pop.web.app.models.service.IUtilidadesService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	@Autowired
	private DataSource dataSource;
	
	// Path scripts bd
	@Value("${path.scripts.bd}")
	private String pathScriptsBd;

	// Pagina de 'Tipo de administracion'
	@GetMapping("")
	public String gestionar(Model model, Locale locale) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/acciones";
	}
	
	// Pagina de 'Carga de script bbdd'
	@GetMapping("/upload")
	public String cargarScript(Model model, Locale locale) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 4);
		
		return "admin/cargar_script";
	}
	
	// Upload fichero script bbdd
	@PostMapping("/upload")
	public String procesarScript(Model model, @RequestParam("file") MultipartFile scriptFile, Locale locale) throws Exception { 
		
		String error = null;
		String success = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		// Si no se ha seleccionado ningún fichero...	
		boolean valido = true;
		if (scriptFile.isEmpty()) {
			error = messageSource.getMessage("text.admin.script.error.1", null, locale);
			LOGGER.info("Archivo de script inexistente o vacío");
			valido = false;
		} else {
			String formato = scriptFile.getOriginalFilename().substring(scriptFile.getOriginalFilename().length()-4);
			if (!".sql".equals(formato)) {
				error = messageSource.getMessage("text.admin.script.error.2", null, locale);
				LOGGER.info("Formato de archivo de script incorrecto");
				valido = false;
			}
		}
				
		if (!valido) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			model.addAttribute("error", error);
			utilidadesService.activarOpcionMenu(model, 4);
			return "admin/cargar_script";
		}
		
		try {
			
			String uniqueFileNameId = UUID.randomUUID().toString() 
					+ "_" + dateFormat.format(new Date()) + "_script_bbdd.csv";
				
			//Path path = Paths.get("C://app//admin//scriptsbd//" + uniqueFileNameId);
			Path path = Paths.get(pathScriptsBd + uniqueFileNameId);

			Files.copy(scriptFile.getInputStream(), path);

			ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
			scriptRunner.setDelimiter(";");
			scriptRunner.setStopOnError(true);
			scriptRunner.setAutoCommit(false);
		
			Reader reader = Files.newBufferedReader(path);
			scriptRunner.runScript(reader);
			  
			// Leemos el fichero
			List<String> lines = Files.readAllLines(Paths.get(path.toUri()),
			        StandardCharsets.UTF_8);
			for (String line : lines) {
				LOGGER.info(line);
			}
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.admin.script.error.3", null, locale);
			LOGGER.info("Ejecución de script de base de datos incorrecta");
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
			model.addAttribute("error", error);
			utilidadesService.activarOpcionMenu(model, 4);
			return "admin/cargar_script";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.admin.title", null, locale));
		success = messageSource.getMessage("text.admin.script.ok", null, locale);
		model.addAttribute("success", success);
		LOGGER.info("Script de base de datos ejecutado correctamente");
		utilidadesService.activarOpcionMenu(model, 4);
		return "admin/cargar_script";
	}
		
}
