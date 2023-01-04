package com.tfg.look4pop.web.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.look4pop.web.app.constants.Look4PopConstants;
import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.ConsultaFormDescripcionDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.entity.Territorio;
import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;
import com.tfg.look4pop.web.app.models.service.IFuenteService;
import com.tfg.look4pop.web.app.models.service.IPoblacionService;
import com.tfg.look4pop.web.app.models.service.ITerritorioService;
import com.tfg.look4pop.web.app.models.service.ITipoTerritorioService;
import com.tfg.look4pop.web.app.models.service.IUtilidadesService;
import com.tfg.look4pop.web.app.validations.ConsultaFormValidador;

@Controller
@RequestMapping("/consulta")
@RemoteProxy(name = "consultaController")
public class ConsultaController {
	
	private static final Logger LOGGER = LogManager.getLogger(ConsultaController.class);
	
	@Autowired
	private ITipoTerritorioService tipoTerritorioService;
	
	@Autowired
	private ITerritorioService territorioService;
	
	@Autowired
	private IFuenteService fuenteService;
	
	@Autowired
	private IPoblacionService poblacionService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ConsultaFormValidador consultaFormValidador;
	
	// Proceso de mapear (poblar) y validar el formulario. Se hace de forma transparente
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Registramos (añadimo) nuestro validador personalizado
		binder.addValidators(consultaFormValidador);
	}
	
	// Maximo registros obtenidos por consulta
	@Value("${query.max.registros}")
	private Integer sqlMaxRegs;
	
	// Path form downloads
	@Value("${path.form.downloads}")
	private String pathFormDownloads;
		
	// Path form uploads
	@Value("${path.form.uploads}")
	private String pathFormUploads;
	
	@Autowired
	private IUtilidadesService utilidadesService;
	
	// Formulario de consulta
	@GetMapping("/form")
	public String crearConsultaByForm(Model model, Locale locale, HttpServletRequest request) {
		ConsultaFormDTO consultaForm = new ConsultaFormDTO();
		model.addAttribute("consultaForm", consultaForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
		
		request.getSession().removeAttribute("consultaForm"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("consultaFormDesc"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("poblacionDataAll"); // Para eliminar el atributo de la sesion (si existe)
		
		utilidadesService.activarOpcionMenu(model, 2);
		
		return "consulta/form";
	}
	
	// Upload del fichero de consulta
	@GetMapping("/upload")
	public String crearConsultaByFile(Model model, Locale locale, HttpServletRequest request) {
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
		
		request.getSession().removeAttribute("consultaForm"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("consultaFormDesc"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("poblacionDataAll"); // Para eliminar el atributo de la sesion (si existe)
		
		utilidadesService.activarOpcionMenu(model, 3);
		return "consulta/upload";
	}
	
	// Procesamiento formulario de consulta
	@PostMapping(path = "/form", params = "consultar")
	public String procesarConsultaByForm(@Valid @ModelAttribute("consultaForm") ConsultaFormDTO consultaForm, BindingResult result, Model model, Locale locale, HttpServletRequest request) {
		
		String error = null;
		
		// Si el formulario contiene errores de validacion...
		if (result.hasErrors()) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			return "consulta/form";
		}
		
		try {
			
			// Carga de registros inicial
			consultaForm.setNumElementAct(0);
			consultaForm.setNumMaxRegs(sqlMaxRegs);
			LOGGER.info("Límite máximo registros a mostrar (por defecto): " + sqlMaxRegs);
			
			// Se gestionan los anios seleccionados
			gestionarAniosConsulta(consultaForm);
					
			List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>(); 
			List<PoblacionDataDTO> poblacionData = new ArrayList<PoblacionDataDTO>(); 
			// Dependiendo del nivel de detalle seleccionado, la consulta se gestiona de una u otra manera
			if (Look4PopConstants.TP_TERRITORIO_MUNICIPIO.equals(consultaForm.getNivel())) {
				// Nivel 'Municipio'
				poblacionDataAll = poblacionService.getPoblacionByMunicipioLst(consultaForm);
			} else {
				// Nivel distinto a 'Municipio'
				poblacionDataAll = poblacionService.getPoblacionLst(consultaForm);
			}
			consultaForm.setNumTotRegs(poblacionDataAll.size());
					
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("poblacionDataAll", poblacionDataAll);
			
			// Registros a mostrar en pantalla
			poblacionData = poblacionDataAll.stream().limit(consultaForm.getNumElementAct() + consultaForm.getNumMaxRegs())
					.collect(Collectors.toList());
			consultaForm.setNumElementAct(poblacionData.size());
			
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("consultaForm", consultaForm);
							
			// Se cargan los filtros de consulta para mostrarlos en pantalla
			ConsultaFormDescripcionDTO consultaFormDesc = getConsultaFormDescripcion(consultaForm);
			
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("consultaFormDesc", consultaFormDesc);
		
		} catch (Exception e) {
			error = messageSource.getMessage("text.consulta.error.1", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("Ha ocurrido un error al realizar la consulta");
			return "consulta/form";
		}
		
		return "redirect:/consulta/listar";
	}
	
	@PostMapping(path = "/form", params = "guardar")
	public Object guardarConsulta(@ModelAttribute("consultaForm") ConsultaFormDTO consultaForm, @RequestParam(value = "descripcion", required = false) String descripcion, Model model, Locale locale, HttpServletRequest request) throws IOException {
					
		String error = null;
		Resource resource = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		try {
		
			String uniqueFileNameId = UUID.randomUUID().toString() 
					+ "_" + dateFormat.format(new Date()) + "_download_queryForm.csv";
						
			//Path path = Paths.get("C://app//queryforms//downloads//" + uniqueFileNameId);
			Path path = Paths.get(pathFormDownloads + uniqueFileNameId);
			File archivo = path.toFile();
	
			if (archivo.exists() && archivo.canRead()) { // El archivo existe y se puede leer
				archivo.delete();
			}
			
			// Se compone el fichero de consulta
			List<String> lineas = componerFicheroConsulta(consultaForm, descripcion);
			
			Files.write(Paths.get(path.toUri()), lineas, 
			        StandardCharsets.UTF_8);
	
			resource = new UrlResource(path.toUri());
		 
		} catch (Exception e) {
			error = messageSource.getMessage("text.consulta.error.2", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.listar.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("Ha ocurrido un error durante la descarga del fichero de consulta");
			return "consulta/listar";
		}
					
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("text/csv"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
				
	}
	
	//@GetMapping("/form/modificar")
	@PostMapping(path = "/form", params = "modificar")
	public Object modificarConsulta(@ModelAttribute("consultaForm") ConsultaFormDTO consultaForm, Model model, Locale locale, HttpServletRequest request) throws IOException {
		List<String> aniosSelec = new ArrayList<String>();
		
		// Censo de derecho
		if (!StringUtils.isEmpty(consultaForm.getCensoDerAnios())) {
			for (int i = 0; i < consultaForm.getCensoDerAnios().length; i++) {
				aniosSelec.add("d" + consultaForm.getCensoDerAnios()[i]);
			}
		}
		
		// Censo de hecho
		if (!StringUtils.isEmpty(consultaForm.getCensoHecAnios())) {
			for (int i = 0; i < consultaForm.getCensoHecAnios().length; i++) {
				aniosSelec.add("h" + consultaForm.getCensoHecAnios()[i]);
			}
		}
				
		// Padron
		if (!StringUtils.isEmpty(consultaForm.getPadronAnios())) {
			for (int i = 0; i < consultaForm.getPadronAnios().length; i++) {
				aniosSelec.add("p" + consultaForm.getPadronAnios()[i]);
			}
		}
		
		consultaForm.setAnios(aniosSelec.toArray(new String[aniosSelec.size()]));
		
		model.addAttribute("consultaForm", consultaForm);
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
		
		request.getSession().removeAttribute("consultaForm"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("consultaFormDesc"); // Para eliminar el atributo de la sesion (si existe)
		request.getSession().removeAttribute("poblacionDataAll"); // Para eliminar el atributo de la sesion (si existe)
		
		utilidadesService.activarOpcionMenu(model, 2);
		
		return "consulta/form";
	}
	
	// Listar
	@GetMapping("/listar")
	public String listarResultados(Model model, Locale locale, HttpServletRequest request) {
		
		String error = null;
		String success = null;
		
		ConsultaFormDTO consultaForm = (ConsultaFormDTO) request.getSession().getAttribute("consultaForm");
		ConsultaFormDescripcionDTO consultaFormDesc = (ConsultaFormDescripcionDTO) request.getSession().getAttribute("consultaFormDesc");
		List<PoblacionDataDTO> poblacionDataAll = (List<PoblacionDataDTO>) request.getSession().getAttribute("poblacionDataAll");
		
		List<PoblacionDataDTO> poblacionData = new ArrayList<PoblacionDataDTO>();
		
		try {
			
			// Registros a mostrar en pantalla
			poblacionData = poblacionDataAll.stream().limit(consultaForm.getNumElementAct())
						.collect(Collectors.toList());
			
			LOGGER.info("Total registros devueltos por la consulta: " + poblacionDataAll.size());
			LOGGER.info("Total registros a mostrar en pantalla: " + poblacionData.size());	
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.consulta.error.3", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("Ha ocurrido durante el listado de los resultados");
			return "consulta/form";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.listar.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 2);
		
		model.addAttribute("consultaForm", consultaForm);
		model.addAttribute("consultaFormDesc", consultaFormDesc);
		model.addAttribute("poblacionData", poblacionData);
			
		success = messageSource.getMessage("text.resultados.listar.ok.1", null, locale) + " " + poblacionData.size() + " " + messageSource.getMessage("text.resultados.listar.ok.2", null, locale) + " " + consultaForm.getNumTotRegs();
		model.addAttribute("success", success);
		LOGGER.info("Resultados mostrados correctamente");
		return "consulta/listar";
	}
	
	// Listar
	@PostMapping("/listar")
	public String listarResultadosExport(Model model, Locale locale, HttpServletRequest request, @RequestParam(value = "desPepito", required = false) String desPepito, @RequestParam(value = "datos", required = false) String datos) {
			
		String error = null;
		String success = null;
		
		ConsultaFormDTO consultaForm = (ConsultaFormDTO) request.getSession().getAttribute("consultaForm");
		ConsultaFormDescripcionDTO consultaFormDesc = (ConsultaFormDescripcionDTO) request.getSession().getAttribute("consultaFormDesc");
		List<PoblacionDataDTO> poblacionDataAll = (List<PoblacionDataDTO>) request.getSession().getAttribute("poblacionDataAll");
		
		List<PoblacionDataDTO> poblacionData = new ArrayList<PoblacionDataDTO>();
		
		try {
			
			// Registros a mostrar en pantalla
			poblacionData = poblacionDataAll.stream().limit(consultaForm.getNumElementAct())
						.collect(Collectors.toList());
			
			LOGGER.info("Total registros devueltos por la consulta: " + poblacionDataAll.size());
			LOGGER.info("Total registros a mostrar en pantalla: " + poblacionData.size());	
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.consulta.error.3", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("Ha ocurrido durante el listado de los resultados");
			return "consulta/form";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.listar.title", null, locale));
		utilidadesService.activarOpcionMenu(model, 2);
		
		model.addAttribute("consultaForm", consultaForm);
		model.addAttribute("consultaFormDesc", consultaFormDesc);
		model.addAttribute("poblacionData", poblacionData);
		model.addAttribute("desPepito", desPepito);
		model.addAttribute("datos", datos);
			
		success = messageSource.getMessage("text.resultados.listar.ok.1", null, locale) + " " + poblacionData.size() + " " + messageSource.getMessage("text.resultados.listar.ok.2", null, locale) + " " + consultaForm.getNumTotRegs();
		model.addAttribute("success", success);
		LOGGER.info("Resultados mostrados correctamente");
		return "consulta/listar";
	}
	
	// Upload del fichero de consulta
	@PostMapping("/upload")
	public String guardar(@RequestParam("file") MultipartFile file, Model model, Locale locale, HttpServletRequest request) throws Exception { 
			
		String error = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		// Si no se ha seleccionado ningún fichero...	
		boolean valido = true;
		if (file.isEmpty()) {
			error = messageSource.getMessage("text.consulta.upload.error.1", null, locale);
			LOGGER.info("Archivo de consulta inexistente o vacío");
			valido = false;
		} else {
			String formato = file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
			if (!".csv".equals(formato)) {
				error = messageSource.getMessage("text.consulta.upload.error.2", null, locale);
				LOGGER.info("Formato de archivo de consulta incorrecto");
				valido = false;
			}
		}
		
		if (!valido) {
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			model.addAttribute("error", error);
			utilidadesService.activarOpcionMenu(model, 2);
			return "consulta/upload";
		}
		
		try {
				
			String uniqueFileNameId = UUID.randomUUID().toString() 
					+ "_" + dateFormat.format(new Date()) + "_upload_queryForm.csv";
				
			Path path = Paths.get(pathFormUploads + uniqueFileNameId);

			Files.copy(file.getInputStream(), path);
		
			// Leemos el fichero
			List<String> lineas = Files.readAllLines(Paths.get(path.toUri()),
			        StandardCharsets.UTF_8);
			
			ConsultaFormDTO consultaForm = obtenerParamsConsulta(lineas);
			
			// Carga de registros inicial
			consultaForm.setNumElementAct(0);
			consultaForm.setNumMaxRegs(sqlMaxRegs);
			LOGGER.info("Límite máximo registros a mostrar (por defecto): " + sqlMaxRegs);
					
			List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>(); 
			List<PoblacionDataDTO> poblacionData = new ArrayList<PoblacionDataDTO>(); 
			
			// Dependiendo del nivel de detalle seleccionado, la consulta se gestiona de una u otra manera
			if (Look4PopConstants.TP_TERRITORIO_MUNICIPIO.equals(consultaForm.getNivel())) {
				// Nivel 'Municipio'
				poblacionDataAll = poblacionService.getPoblacionByMunicipioLst(consultaForm);
			} else {
				// Nivel distinto a 'Municipio'
				poblacionDataAll = poblacionService.getPoblacionLst(consultaForm);
			}
			consultaForm.setNumTotRegs(poblacionDataAll.size());
					
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("poblacionDataAll", poblacionDataAll);
			
			// Registros a mostrar en pantalla
			poblacionData = poblacionDataAll.stream().limit(consultaForm.getNumElementAct() + consultaForm.getNumMaxRegs())
					.collect(Collectors.toList());
			consultaForm.setNumElementAct(poblacionData.size());
			
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("consultaForm", consultaForm);
							
			// Se cargan los filtros de consulta para mostrarlos en pantalla
			ConsultaFormDescripcionDTO consultaFormDesc = getConsultaFormDescripcion(consultaForm);
			
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("consultaFormDesc", consultaFormDesc);
				
		} catch (Exception e) {
			error = messageSource.getMessage("text.consulta.upload.error.3", null, locale);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.consulta.title", null, locale));
			model.addAttribute("error", error);
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("La estructura interna del fichero es errónea. Ha ocurrido un error al realizar la consulta");
			return "consulta/upload";
		}
		
		return "redirect:/consulta/listar";	
	}
	
	// Carga mas registros en la query de consulta
	@GetMapping("/cargar")
	public String cargarMasRegistros(Model model, Locale locale, HttpServletRequest request) {
		
		String error = null;
		String success = null;
				
		ConsultaFormDTO consultaForm = (ConsultaFormDTO) request.getSession().getAttribute("consultaForm");
		ConsultaFormDescripcionDTO consultaFormDesc = (ConsultaFormDescripcionDTO) request.getSession().getAttribute("consultaFormDesc");
		List<PoblacionDataDTO> poblacionDataAll = (List<PoblacionDataDTO>) request.getSession().getAttribute("poblacionDataAll");
		
		List<PoblacionDataDTO> poblacionData = new ArrayList<PoblacionDataDTO>();
		
		try {
			
			poblacionData = poblacionDataAll;
			consultaForm.setNumElementAct(poblacionData.size());
			
			// Se aniade el atributo a la sesion
			request.getSession().setAttribute("consultaForm", consultaForm);
			
			LOGGER.info("Total registros a mostrar en pantalla: " + poblacionData.size());
			
		} catch (Exception e) {
			error = messageSource.getMessage("text.resultados.listar.cargarmas.error", null, locale);
			model.addAttribute("error", error);
			model.addAttribute("tituloPagina", messageSource.getMessage("text.page.listar.title", null, locale));
			utilidadesService.activarOpcionMenu(model, 2);
			LOGGER.info("Ha ocurrido un error al cargar más registros en pantalla");
			return "consulta/listar";
		}
		
		model.addAttribute("tituloPagina", messageSource.getMessage("text.page.listar.title", null, locale));
		
		model.addAttribute("consultaForm", consultaForm);
		model.addAttribute("consultaFormDesc", consultaFormDesc);
		model.addAttribute("poblacionData", poblacionData);
		
		success = messageSource.getMessage("text.resultados.listar.ok.1", null, locale) + " " + poblacionData.size() + " " + messageSource.getMessage("text.resultados.listar.ok.2", null, locale) + " " + consultaForm.getNumTotRegs();
		model.addAttribute("success", success);
		LOGGER.info("Nuevos registros cargados correctamente");
		return "redirect:/consulta/listar";
	}
	
	private void gestionarAniosConsulta(ConsultaFormDTO consultaForm) {
		
		List<String> censoDerAnioLst = new ArrayList<String>();
		List<String> censoHecAnioLst = new ArrayList<String>();
		List<String> padronAnioLst = new ArrayList<String>();
		
		List<String> fuenteLst = Arrays.asList(consultaForm.getAnios());
		
		for (String fuente : fuenteLst) {
			
			Character tpFuente = fuente.charAt(0);
			String anio = fuente.substring(1);
			
			// Anio censo de derecho
			if ("d".equals(tpFuente.toString())) {
				censoDerAnioLst.add(anio);
			}
			
			// Anio censo de hecho
			if ("h".equals(tpFuente.toString())) {
				censoHecAnioLst.add(anio);
			}
			
			// Anio padron
			if ("p".equals(tpFuente.toString())) {
				padronAnioLst.add(anio);
			}
			
		}
		
		consultaForm.setCensoDerAnios(censoDerAnioLst.toArray(new String[censoDerAnioLst.size()]));
		consultaForm.setCensoHecAnios(censoHecAnioLst.toArray(new String[censoHecAnioLst.size()]));
		consultaForm.setPadronAnios(padronAnioLst.toArray(new String[padronAnioLst.size()]));
	}
	
	private ConsultaFormDTO obtenerParamsConsulta(List<String> lineas) {
		
		ConsultaFormDTO consultaForm = new ConsultaFormDTO();
		
		List<String> ambParLst = new ArrayList<String>();
		List<String> tpFuenteLst = new ArrayList<String>();
		List<String> censoDerAnioLst = new ArrayList<String>();
		List<String> censoHecAnioLst = new ArrayList<String>();
		List<String> padronAnioLst = new ArrayList<String>();
		
		for (String linea : lineas) {
			
			StringTokenizer st = new StringTokenizer(linea, ";");
			
			Integer lineaId = Integer.parseInt(st.nextToken());
			st.nextToken(); // Elemento a modo informativo. No es necesario
				
			switch(lineaId) {
				case 1:
					String nivel = st.nextToken().trim();
					if (!StringUtils.isEmpty(nivel)) {
						consultaForm.setNivel(nivel);
					}	
				break;
				case 2:
					String ambGen = st.nextToken().trim();
					if (!StringUtils.isEmpty(ambGen)) {
						consultaForm.setAmbitoGen(ambGen);
					}
				break;
				case 3:
					String ambPar = st.nextToken().trim();
					if (!StringUtils.isEmpty(ambPar)) {
						ambParLst.add(ambPar);
					}	
				break;
				case 4:
					String tpFuente = st.nextToken().trim();
					if (!StringUtils.isEmpty(tpFuente)) {
						tpFuenteLst.add(tpFuente);
					}
				break;
				case 5:
					String censoDerAnio = st.nextToken().trim();
					if (!StringUtils.isEmpty(censoDerAnio)) {
						censoDerAnioLst.add(censoDerAnio);
					}
				break;
				case 6:
					String censoHecAnio = st.nextToken().trim();
					if (!StringUtils.isEmpty(censoHecAnio)) {
						censoHecAnioLst.add(censoHecAnio);
					}
				break;
				case 7:
					String padronAnio = st.nextToken().trim();
					if (!StringUtils.isEmpty(padronAnio)) {
						padronAnioLst.add(padronAnio);
					}
			}
		
		}	
		
		// Poblamos el objeto contenedor con los correspondientes parametros
		consultaForm.setAmbitosPar(ambParLst.toArray(new String[ambParLst.size()]));
		consultaForm.setTpsFuente(tpFuenteLst.toArray(new String[tpFuenteLst.size()]));
		consultaForm.setCensoDerAnios(censoDerAnioLst.toArray(new String[censoDerAnioLst.size()]));
		consultaForm.setCensoHecAnios(censoHecAnioLst.toArray(new String[censoHecAnioLst.size()]));
		consultaForm.setPadronAnios(padronAnioLst.toArray(new String[padronAnioLst.size()]));
		
		return consultaForm;
	}
	
	// Se compone el fichero de consulta
	private List<String> componerFicheroConsulta(ConsultaFormDTO consultaForm, String descripcion) {
		
		List<String> lineas = new ArrayList<String>();
		
		String lineaDescripcionId = "0;Descripcion;";
		String lineaNivelId = "1;Nivel;";
		String lineaAmbGenId = "2;AmbitoGeneral;";
		String lineaAmbParId = "3;AmbitoParticular;";
		String lineaTpFuenteId = "4;TipoFuente;";
		String lineaCensoDerAnioId = "5;CensoDerechoAnio;";
		String lineaCensoHecAnioId = "6;CensoHechoAnio;";
		String lineaPadronAnioId = "7;PadronAnio;";
		
		// Linea 'Nivel'
		String lineaNivel = "";
		TipoTerritorio tipoTerritorioNivel = getConsultaFormTpTerritorio(consultaForm, 1);
		if (null != tipoTerritorioNivel) {
			lineaNivel = lineaNivelId + tipoTerritorioNivel.getIdTt() + ";" + tipoTerritorioNivel.getNombre();
		} else {
			lineaNivel = lineaNivelId + " ; ";
		}
		
		// Linea 'Ambito general'
		String lineaAmbGen = "";
		TipoTerritorio tipoTerritorioAmbGen = getConsultaFormTpTerritorio(consultaForm, 2);
		if (null != tipoTerritorioAmbGen) {
			lineaAmbGen = lineaAmbGenId + tipoTerritorioAmbGen.getIdTt() + ";" + tipoTerritorioAmbGen.getNombre();
		} else {
			lineaAmbGen = lineaAmbGenId + " ; ";
		}
		
		// Lineas 'Ambito particular'
		String[] lineasAmbPar = null;
		Territorio[] territoriosAmbPar = getConsultaFormTerritorios(consultaForm);
		if ((null != territoriosAmbPar) && (territoriosAmbPar.length > 0)) {
			lineasAmbPar = new String[territoriosAmbPar.length];
			for (int i = 0; i < territoriosAmbPar.length; i++) {
				lineasAmbPar[i] = lineaAmbParId + territoriosAmbPar[i].getIdTerritorio() + ";" + territoriosAmbPar[i].getNombreActual() + " [" + territoriosAmbPar[i].getCodigoOficial() + "]";
			}
		} else {
			lineasAmbPar = new String[1];
			lineasAmbPar[0] = lineaAmbParId + " ; ";
		}
		
		// Lineas 'Tipo de fuente'
		String[] lineasTpFuente = null;		
		if ((null != consultaForm.getTpsFuente()) && (consultaForm.getTpsFuente().length > 0)) {
			lineasTpFuente = new String[consultaForm.getTpsFuente().length];
			for (int i = 0; i < consultaForm.getTpsFuente().length; i++) {
				lineasTpFuente[i] = lineaTpFuenteId + consultaForm.getTpsFuente()[i];
			}
		} else {
			lineasTpFuente = new String[1];
			lineasTpFuente[0] = lineaTpFuenteId + " ";
		}
		
		// Lineas 'Censo Derecho Anio'
		String[] lineasCensoDerAnio = null;		
		if ((null != consultaForm.getCensoDerAnios()) && (consultaForm.getCensoDerAnios().length > 0)) {
			lineasCensoDerAnio = new String[consultaForm.getCensoDerAnios().length];
			for (int i = 0; i < consultaForm.getCensoDerAnios().length; i++) {
				lineasCensoDerAnio[i] = lineaCensoDerAnioId + consultaForm.getCensoDerAnios()[i];
			}
		} else {
			lineasCensoDerAnio = new String[1];
			lineasCensoDerAnio[0] = lineaCensoDerAnioId + " ";
		}
		
		// Lineas 'Censo Hecho Anio'
		String[] lineasCensoHecAnio = null;		
		if ((null != consultaForm.getCensoHecAnios()) && (consultaForm.getCensoHecAnios().length > 0)) {
			lineasCensoHecAnio = new String[consultaForm.getCensoHecAnios().length];
			for (int i = 0; i < consultaForm.getCensoHecAnios().length; i++) {
				lineasCensoHecAnio[i] = lineaCensoHecAnioId + consultaForm.getCensoHecAnios()[i];
			}
		} else {
			lineasCensoHecAnio = new String[1];
			lineasCensoHecAnio[0] = lineaCensoHecAnioId + " ";
		}
		
		// Lineas 'Padron Anio'
		String[] lineasPadronAnio = null;		
		if ((null != consultaForm.getPadronAnios()) && (consultaForm.getPadronAnios().length > 0)) {
			lineasPadronAnio = new String[consultaForm.getPadronAnios().length];
			for (int i = 0; i < consultaForm.getPadronAnios().length; i++) {
				lineasPadronAnio[i] = lineaPadronAnioId + consultaForm.getPadronAnios()[i];
			}
		} else {
			lineasPadronAnio = new String[1];
			lineasPadronAnio[0] = lineaPadronAnioId + " ";
		}
				
		// Poblamos la lista de lineas a informar en el fichero
		lineas.add(lineaDescripcionId + descripcion);
		lineas.add(lineaNivel);
		lineas.add(lineaAmbGen);
	
		for (int i = 0; i < lineasAmbPar.length; i++) {
			lineas.add(lineasAmbPar[i]);
		}
		
		for (int i = 0; i < lineasTpFuente.length; i++) {
			lineas.add(lineasTpFuente[i]);
		}
		
		for (int i = 0; i < lineasCensoDerAnio.length; i++) {
			lineas.add(lineasCensoDerAnio[i]);
		}
		
		for (int i = 0; i < lineasCensoHecAnio.length; i++) {
			lineas.add(lineasCensoHecAnio[i]);
		}
		
		for (int i = 0; i < lineasPadronAnio.length; i++) {
			lineas.add(lineasPadronAnio[i]);
		}
		
		return lineas;
	}
	
	
	private ConsultaFormDescripcionDTO getConsultaFormDescripcion(ConsultaFormDTO consultaForm) {
		
		ConsultaFormDescripcionDTO consultaFormDesc = new ConsultaFormDescripcionDTO();
		
		// Descripcion 'Nivel'
		String nivelDesc = "";
		TipoTerritorio tipoTerritorioNivel = getConsultaFormTpTerritorio(consultaForm, 1);
		if (null != tipoTerritorioNivel) {
			nivelDesc = tipoTerritorioNivel.getNombre();
		}
		
		// Descripcion 'Ambito general'
		String ambitoGenDesc = "";
		TipoTerritorio tipoTerritorioAmbGen = getConsultaFormTpTerritorio(consultaForm, 2);
		if (null != tipoTerritorioAmbGen) {
			ambitoGenDesc = tipoTerritorioAmbGen.getNombre();
		}
		
		// Descripcion 'Ambitos particulares'
		String[] ambitosParDesc = null;
		Territorio[] territoriosAmbPar = getConsultaFormTerritorios(consultaForm);
		if ((null != territoriosAmbPar) && (territoriosAmbPar.length > 0)) {
			ambitosParDesc = new String[territoriosAmbPar.length];
			for (int i = 0; i < territoriosAmbPar.length; i++) {
				ambitosParDesc[i] = territoriosAmbPar[i].getNombreActual() + " [" + territoriosAmbPar[i].getCodigoOficial() + "]";
			}
		} else {
			ambitosParDesc = new String[1];
			ambitosParDesc[0] = "";
		}
		
		consultaFormDesc.setNivelDesc(nivelDesc);
		consultaFormDesc.setAmbitoGenDesc(ambitoGenDesc);
		consultaFormDesc.setAmbitosParDesc(ambitosParDesc);
		consultaFormDesc.setAmbitosParDescFormat(String.join(",\n", Arrays.asList(ambitosParDesc)));
		
		if ((null != consultaForm.getCensoDerAnios()) && consultaForm.getCensoDerAnios().length > 0) {
			consultaFormDesc.setCensoDerAniosDesc(consultaForm.getCensoDerAnios());
			consultaFormDesc.setCensoDerAniosDescFormat(String.join(",\n", Arrays.asList(consultaForm.getCensoDerAnios())));
		} else {
			consultaFormDesc.setCensoDerAniosDesc(new String[] {""});
			consultaFormDesc.setCensoDerAniosDescFormat("");
		}
		
		if ((null != consultaForm.getCensoHecAnios()) && consultaForm.getCensoHecAnios().length > 0) {
			consultaFormDesc.setCensoHecAniosDesc(consultaForm.getCensoHecAnios());
			consultaFormDesc.setCensoHecAniosDescFormat(String.join(",\n", Arrays.asList(consultaForm.getCensoHecAnios())));
		} else {
			consultaFormDesc.setCensoHecAniosDesc(new String[] {""});
			consultaFormDesc.setCensoHecAniosDescFormat("");
		}
		
		if ((null != consultaForm.getPadronAnios()) && consultaForm.getPadronAnios().length > 0) {
			consultaFormDesc.setPadronAniosDesc(consultaForm.getPadronAnios());
			consultaFormDesc.setPadronAniosDescFormat(String.join(",\n", Arrays.asList(consultaForm.getPadronAnios())));
		} else {
			consultaFormDesc.setPadronAniosDesc(new String[] {""});
			consultaFormDesc.setPadronAniosDescFormat("");
		}
		
		return consultaFormDesc;
	}
	
	// TipoTerritorio 'Nivel' / 'Ambito general'
	private TipoTerritorio getConsultaFormTpTerritorio(ConsultaFormDTO consultaForm, Integer nivamb) {
		
		// nivamb: 1 -> Nivel
		// nivamb: 2 -> Ambito (general)
				
		TipoTerritorio tipoTerritorio = null;
		String nivelAmbitoId = "";
				
		switch(nivamb) {
			case 1:
				nivelAmbitoId = consultaForm.getNivel();
			break;
			case 2:
				nivelAmbitoId = consultaForm.getAmbitoGen();		
		}
				
		if (!StringUtils.isEmpty(nivelAmbitoId)) {
			tipoTerritorio = tipoTerritorioService.getTipoTerritorioById(nivelAmbitoId);
		}
				
		return tipoTerritorio;
	}
	
	// Territorio 'Ambito particular'
	private Territorio[] getConsultaFormTerritorios(ConsultaFormDTO consultaForm) {
					
		Territorio[] territorios = null;
					
		String[] ambitosParId = consultaForm.getAmbitosPar();
		if ((null != ambitosParId) && (ambitosParId.length > 0)) {
			territorios = new Territorio[ambitosParId.length];
			for (int i = 0; i < ambitosParId.length; i++) {
				Territorio territorio = territorioService.getTerritorioById(ambitosParId[i]);
				territorios[i] = territorio;
			}
		} 
					
		return territorios;
	}
	
	@RemoteMethod
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(String nivel) {
		return tipoTerritorioService.getTipoTerritorioAmbitoGenLst(nivel);
	}
		
	@RemoteMethod
	public List<Territorio> getTerritorioAmbitoParLst(String nivel, String ambitoGen) {
		return territorioService.getTerritorioAmbitoParLst(nivel, ambitoGen);
	}
	
	@RemoteMethod
	public List<Fuente> getFuenteCensoByTipoLst(String tpCenso) {
		return fuenteService.getFuenteCensoByTipoLst(tpCenso);
	}
		
	@RemoteMethod
	public List<Fuente> getFuentePadronLst() {
		return fuenteService.getFuentePadronLst();
	}
			
	@ModelAttribute
	public void gestionaModelos(ConsultaFormDTO consultaForm, Model model, Locale locale) throws Exception {
			
		// Carga niveles
		model.addAttribute("selectNiveles", tipoTerritorioService.getTipoTerritorioNivelLst());
			
		// Carga ambitos (generales)
		if ( (!StringUtils.isEmpty(consultaForm.getNivel())) 
				&& (!"-1".equals(consultaForm.getNivel())) ) {
			model.addAttribute("selectAmbitosGen", tipoTerritorioService.getTipoTerritorioAmbitoGenLst(consultaForm.getNivel()));
		}
			
		// Carga ambitos (particulares)
		if ( (!StringUtils.isEmpty(consultaForm.getNivel())) 
				&& (!"-1".equals(consultaForm.getNivel()))
				&& (!StringUtils.isEmpty(consultaForm.getAmbitoGen())) 
				&& (!"-1".equals(consultaForm.getAmbitoGen())) ) {
			model.addAttribute("selectAmbitosPar", territorioService.getTerritorioAmbitoParLst(consultaForm.getNivel(), consultaForm.getAmbitoGen()));
		}
		
		// Carga anios
		if (!StringUtils.isEmpty(consultaForm.getTpsFuente())) {
					
			List<String> tipoFuenteLst = Arrays.asList(consultaForm.getTpsFuente());
			List<Fuente> fuenteLst = new ArrayList<Fuente>();
			List<Fuente> fuenteFormatLst = new ArrayList<Fuente>();
			
			// Censo de derecho
			if (tipoFuenteLst.contains("censoderecho")) {
				List<Fuente> cenDerAnioLst = new ArrayList<Fuente>();
				cenDerAnioLst = fuenteService.getFuenteCensoByTipoLst("derecho");
				fuenteLst.addAll(cenDerAnioLst);
			}
			
			// Censo de hecho
			if (tipoFuenteLst.contains("censohecho")) {
				List<Fuente> cenHecAnioLst = new ArrayList<Fuente>();
				cenHecAnioLst = fuenteService.getFuenteCensoByTipoLst("hecho");
				fuenteLst.addAll(cenHecAnioLst);
			}
			
			// Padron
			if (tipoFuenteLst.contains("padron")) {
				List<Fuente> padronAnioLst = new ArrayList<Fuente>();
				padronAnioLst = fuenteService.getFuentePadronLst();
				fuenteLst.addAll(padronAnioLst);
			}
			
			// Formateo de datos	
			for (Fuente fuente : fuenteLst) {
				
				if ( ("censo".equals(fuente.getTipo()))
						&& ("derecho".equals(fuente.getSubtipo())) ) {
					
					fuente.setTipo(fuente.getAnio() + " - (" + messageSource.getMessage("text.form.tipofuente.censo.derecho", null, locale) + ")");
					fuente.setAnio("d" + fuente.getAnio());
				}
				
				if ( ("censo".equals(fuente.getTipo()))
						&& ("hecho".equals(fuente.getSubtipo())) ) {
					
					fuente.setTipo(fuente.getAnio() + " - (" + messageSource.getMessage("text.form.tipofuente.censo.hecho", null, locale) + ")");
					fuente.setAnio("h" + fuente.getAnio());
				}
				
				if ("padrón".equals(fuente.getTipo())) {
					
					fuente.setTipo(fuente.getAnio() + " - (" + messageSource.getMessage("text.form.tipofuente.padron", null, locale) + ")");
					fuente.setAnio("p" + fuente.getAnio());
				}
				
				fuenteFormatLst.add(fuente);
			}
			
			model.addAttribute("selectAnios", fuenteFormatLst);	
		}
			
	}		

}
