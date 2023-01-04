package com.tfg.look4pop.web.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
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

@SpringBootTest
public class ConsultaControllerMockTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Mock
	private ITipoTerritorioService tipoTerritorioService;
	
	@Mock
	private ITerritorioService territorioService;
	
	@Mock
	private IFuenteService fuenteService;
	
	@Mock
	private IPoblacionService poblacionService;
	
	@Mock
	Model model;
	
	final Locale locale = null;
	
	@InjectMocks // auto inject tipoTerritorioService, territorioService and poblacionService
	private ConsultaController consultaController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		consultaController = context.getBean(ConsultaController.class);
		
		this.mockMvc = MockMvcBuilders
				  .standaloneSetup(consultaController)
			      .build();
		MockitoAnnotations.initMocks(this);
		
		// 'selectNiveles' model attribute
		when(tipoTerritorioService.getTipoTerritorioNivelLst()).thenReturn(new ArrayList<TipoTerritorio>());
	}
	
	// Test crearConsultaByForm
	@DisplayName("ConsultaController -> crearConsultaByForm")
	@Test
	void testCrearConsultaByForm() throws Exception {
        this.mockMvc
        	.perform(MockMvcRequestBuilders.get("/consulta/form"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("consulta/form"));
	}
	
	// Test upload
	@DisplayName("ConsultaController -> upload")
	@Test
	void testUpload() throws Exception {
        this.mockMvc
        	.perform(MockMvcRequestBuilders.get("/consulta/upload"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("consulta/upload"));
	}
	
	// Test procesarConsultaByForm
	@DisplayName("ConsultaController -> procesarConsultaByForm")
	@Test
	void testProcesarConsultaByForm() throws Exception {
        // Error validacion form
		ConsultaFormDTO consultaFormError = new ConsultaFormDTO();
		
		this.mockMvc
        	.perform(MockMvcRequestBuilders.post("/consulta/form")
        		.param("consultar", "")
				.flashAttr("consultaForm", consultaFormError)
				.with(csrf()))
        	.andExpect(status().isOk())
			.andExpect(view().name("consulta/form"));
		
		// Consulta OK
		ConsultaFormDTO consultaFormRequest = new ConsultaFormDTO();
		Look4PopConstants constants = new Look4PopConstants();
		consultaFormRequest.setNivel(constants.TP_TERRITORIO_MUNICIPIO);
		consultaFormRequest.setAmbitoGen("M");
		consultaFormRequest.setAmbitosPar(new String[] {"7051"});
		consultaFormRequest.setTpsFuente(new String[] {"censoderecho"});
		consultaFormRequest.setAnios(new String[] {"d1900"});
		
		ConsultaFormDTO consultaFormQuery = new ConsultaFormDTO();
		consultaFormQuery.setNivel("M");
		consultaFormQuery.setAmbitoGen("M");
		consultaFormQuery.setAmbitosPar(new String[] {"7051"});
		consultaFormQuery.setTpsFuente(new String[] {"censoderecho"});
		consultaFormQuery.setAnios(new String[] {"d1900"});
		consultaFormQuery.setCensoDerAnios(new String[] {"1900"});
		consultaFormQuery.setCensoHecAnios(new String[0]);
		consultaFormQuery.setPadronAnios(new String[0]);
		consultaFormQuery.setIdsTerritorioNivel(null);
		consultaFormQuery.setNumElementAct(0);
		consultaFormQuery.setNumMaxRegs(1000);
		consultaFormQuery.setNumTotRegs(null);
		
		List<PoblacionDataDTO> mockPoblacion = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionVilla = new PoblacionDataDTO();
		poblacionVilla.setAnio("1900");
		poblacionVilla.setCodigoOficial("45193");
		poblacionVilla.setNombre("Villanueva de Bogas");
		poblacionVilla.setPoblacion("830");
		poblacionVilla.setTipoFuente("censo derecho");
		poblacionVilla.setTipoTerritorio("Municipio");
		mockPoblacion.add(poblacionVilla);
		
		TipoTerritorio mockTipoTerritorio = new TipoTerritorio("M", "Municipio", null, "E,R,P,AU,C,I,PJ,M");
		
		Territorio mockTerritorioVilla = new Territorio();
		mockTerritorioVilla.setNombreActual("Villanueva de Bogas");
		mockTerritorioVilla.setCodigoOficial("45193");

		when(poblacionService.getPoblacionByMunicipioLst(consultaFormQuery)).thenReturn(mockPoblacion);
		when(tipoTerritorioService.getTipoTerritorioById("M")).thenReturn(mockTipoTerritorio);
		when(territorioService.getTerritorioById("7051")).thenReturn(mockTerritorioVilla);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.post("/consulta/form")
    				.param("consultar", "")
    				.flashAttr("consultaForm", consultaFormRequest)
    				.with(csrf()))
    		.andExpect(redirectedUrl("/consulta/listar"));
	}
	
	// Test guardarConsulta
	@DisplayName("ConsultaController -> guardarConsulta")
	@Test
	void testGuardarConsulta() throws Exception {
		// Guardar consulta OK
		ConsultaFormDTO consultaFormQuery = new ConsultaFormDTO();
		consultaFormQuery.setNivel("M");
		consultaFormQuery.setAmbitoGen("M");
		consultaFormQuery.setAmbitosPar(new String[] {"7051"});
		consultaFormQuery.setTpsFuente(new String[] {"censoderecho"});
		consultaFormQuery.setAnios(null);
		consultaFormQuery.setCensoDerAnios(new String[] {"1900"});
		consultaFormQuery.setCensoHecAnios(new String[0]);
		consultaFormQuery.setPadronAnios(new String[0]);
		consultaFormQuery.setIdsTerritorioNivel(null);
		consultaFormQuery.setNumElementAct(null);
		consultaFormQuery.setNumMaxRegs(null);
		consultaFormQuery.setNumTotRegs(null);
		
		TipoTerritorio mockTipoTerritorio = new TipoTerritorio("M", "Municipio", null, "E,R,P,AU,C,I,PJ,M");
		
		Territorio mockTerritorioVilla = new Territorio();
		mockTerritorioVilla.setIdTerritorio(7051);
		mockTerritorioVilla.setNombreActual("Villanueva de Bogas");
		mockTerritorioVilla.setCodigoOficial("45193");

		when(tipoTerritorioService.getTipoTerritorioById("M")).thenReturn(mockTipoTerritorio);
		when(territorioService.getTerritorioById("7051")).thenReturn(mockTerritorioVilla);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.post("/consulta/form")
    				.param("guardar", "")
    				.param("descripcion", "Consulta1")
    				.flashAttr("consultaForm", consultaFormQuery)
    				.with(csrf()))
    		.andExpect(status().isOk());
	}
	
	// Test modificarConsulta
	@DisplayName("ConsultaController -> modificarConsulta")
	@Test
	void testModificarConsulta() throws Exception {
		// Modificar consulta OK
		ConsultaFormDTO consultaFormQuery = new ConsultaFormDTO();
		consultaFormQuery.setNivel("M");
		consultaFormQuery.setAmbitoGen("M");
		consultaFormQuery.setAmbitosPar(new String[] {"7051"});
		consultaFormQuery.setTpsFuente(new String[] {"censoderecho"});
		consultaFormQuery.setAnios(null);
		consultaFormQuery.setCensoDerAnios(new String[] {"1900"});
		consultaFormQuery.setCensoHecAnios(new String[0]);
		consultaFormQuery.setPadronAnios(new String[0]);
		consultaFormQuery.setIdsTerritorioNivel(null);
		consultaFormQuery.setNumElementAct(null);
		consultaFormQuery.setNumMaxRegs(null);
		consultaFormQuery.setNumTotRegs(null);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.post("/consulta/form")
    				.param("modificar", "")
    				.flashAttr("consultaForm", consultaFormQuery)
    				.with(csrf()))
    		.andExpect(status().isOk())
			.andExpect(view().name("consulta/form"));
	}
	
	// Test listarResultados
	@DisplayName("ConsultaController -> listarResultados")
	@Test
	void testListarResultados() throws Exception {
		// Listar consulta OK
		ConsultaFormDTO consultaFormModelAttr = new ConsultaFormDTO();
		consultaFormModelAttr.setNivel("M");
		consultaFormModelAttr.setAmbitoGen("M");
		consultaFormModelAttr.setAmbitosPar(new String[] {"7051"});
		consultaFormModelAttr.setTpsFuente(new String[] {"censoderecho"});
		consultaFormModelAttr.setAnios(new String[] {"d1900"});
		consultaFormModelAttr.setCensoDerAnios(new String[] {"1900"});
		consultaFormModelAttr.setCensoHecAnios(new String[0]);
		consultaFormModelAttr.setPadronAnios(new String[0]);
		consultaFormModelAttr.setIdsTerritorioNivel(null);
		consultaFormModelAttr.setNumElementAct(1);
		consultaFormModelAttr.setNumMaxRegs(1000);
		consultaFormModelAttr.setNumTotRegs(1);
		
		ConsultaFormDescripcionDTO consultaFormDescModelAttr = new ConsultaFormDescripcionDTO();
		consultaFormDescModelAttr.setNivelDesc("Municipio");
		consultaFormDescModelAttr.setAmbitoGenDesc("Municipio");
		consultaFormDescModelAttr.setAmbitosParDesc(new String[] {"Villanueva de Bogas [45193]"});
		consultaFormDescModelAttr.setAmbitosParDescFormat("Villanueva de Bogas [45193]");
		consultaFormDescModelAttr.setCensoDerAniosDesc(new String[] {"1900"});
		consultaFormDescModelAttr.setCensoDerAniosDescFormat("1900");
		consultaFormDescModelAttr.setCensoHecAniosDesc(new String[0]);
		consultaFormDescModelAttr.setCensoHecAniosDescFormat("");
		consultaFormDescModelAttr.setPadronAniosDesc(new String[0]);
		consultaFormDescModelAttr.setPadronAniosDescFormat("");
		
		List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionDataVilla = new PoblacionDataDTO();
		poblacionDataVilla.setTipoTerritorio("Municipio");
		poblacionDataVilla.setNombre("Villanueva de Bogas");
		poblacionDataVilla.setCodigoOficial("45193");
		poblacionDataVilla.setTipoFuente("censo derecho");
		poblacionDataVilla.setAnio("1900");
		poblacionDataVilla.setPoblacion("830");
		poblacionDataAll.add(poblacionDataVilla);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("consultaForm", consultaFormModelAttr);
		session.setAttribute("consultaFormDesc", consultaFormDescModelAttr);
		session.setAttribute("poblacionDataAll", poblacionDataAll);
		request.setSession(session);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.get("/consulta/listar")
    				.session(session)
    				.with(csrf()))
    		.andExpect(status().isOk())
			.andExpect(view().name("consulta/listar"));
	}
	
	// Test listarResultadosExport
	@DisplayName("ConsultaController -> listarResultadosExport")
	@Test
	void testListarResultadosExport() throws Exception {
		// Listar consulta export OK
		ConsultaFormDTO consultaFormModelAttr = new ConsultaFormDTO();
		consultaFormModelAttr.setNivel("M");
		consultaFormModelAttr.setAmbitoGen("M");
		consultaFormModelAttr.setAmbitosPar(new String[] {"7051"});
		consultaFormModelAttr.setTpsFuente(new String[] {"censoderecho"});
		consultaFormModelAttr.setAnios(new String[] {"d1900"});
		consultaFormModelAttr.setCensoDerAnios(new String[] {"1900"});
		consultaFormModelAttr.setCensoHecAnios(new String[0]);
		consultaFormModelAttr.setPadronAnios(new String[0]);
		consultaFormModelAttr.setIdsTerritorioNivel(null);
		consultaFormModelAttr.setNumElementAct(1);
		consultaFormModelAttr.setNumMaxRegs(1000);
		consultaFormModelAttr.setNumTotRegs(1);
		
		ConsultaFormDescripcionDTO consultaFormDescModelAttr = new ConsultaFormDescripcionDTO();
		consultaFormDescModelAttr.setNivelDesc("Municipio");
		consultaFormDescModelAttr.setAmbitoGenDesc("Municipio");
		consultaFormDescModelAttr.setAmbitosParDesc(new String[] {"Villanueva de Bogas [45193]"});
		consultaFormDescModelAttr.setAmbitosParDescFormat("Villanueva de Bogas [45193]");
		consultaFormDescModelAttr.setCensoDerAniosDesc(new String[] {"1900"});
		consultaFormDescModelAttr.setCensoDerAniosDescFormat("1900");
		consultaFormDescModelAttr.setCensoHecAniosDesc(new String[0]);
		consultaFormDescModelAttr.setCensoHecAniosDescFormat("");
		consultaFormDescModelAttr.setPadronAniosDesc(new String[0]);
		consultaFormDescModelAttr.setPadronAniosDescFormat("");
		
		List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionDataVilla = new PoblacionDataDTO();
		poblacionDataVilla.setTipoTerritorio("Municipio");
		poblacionDataVilla.setNombre("Villanueva de Bogas");
		poblacionDataVilla.setCodigoOficial("45193");
		poblacionDataVilla.setTipoFuente("censo derecho");
		poblacionDataVilla.setAnio("1900");
		poblacionDataVilla.setPoblacion("830");
		poblacionDataAll.add(poblacionDataVilla);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("consultaForm", consultaFormModelAttr);
		session.setAttribute("consultaFormDesc", consultaFormDescModelAttr);
		session.setAttribute("poblacionDataAll", poblacionDataAll);
		request.setSession(session);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.post("/consulta/listar")
    				.session(session)
    				.with(csrf()))
    		.andExpect(status().isOk())
			.andExpect(view().name("consulta/listar"));
	}
	
	// Test guardar
	@DisplayName("ConsultaController -> guardar")
	@Test
	void testGuardar() throws Exception {
		// Upload del fichero de consulta OK
		List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionDataVilla = new PoblacionDataDTO();
		poblacionDataVilla.setTipoTerritorio("Municipio");
		poblacionDataVilla.setNombre("Villanueva de Bogas");
		poblacionDataVilla.setCodigoOficial("45193");
		poblacionDataVilla.setTipoFuente("censo derecho");
		poblacionDataVilla.setAnio("1900");
		poblacionDataVilla.setPoblacion("830");
		poblacionDataAll.add(poblacionDataVilla);
		
		ConsultaFormDTO mockConsultaForm = mock(ConsultaFormDTO.class);
		when(poblacionService.getPoblacionByMunicipioLst(mockConsultaForm)).thenReturn(poblacionDataAll);
		
		TipoTerritorio mockTipoTerritorio = new TipoTerritorio("M", "Municipio", null, "E,R,P,AU,C,I,PJ,M");
		
		Territorio mockTerritorioVilla = new Territorio();
		mockTerritorioVilla.setIdTerritorio(7051);
		mockTerritorioVilla.setNombreActual("Villanueva de Bogas");
		mockTerritorioVilla.setCodigoOficial("45193");

		when(tipoTerritorioService.getTipoTerritorioById("M")).thenReturn(mockTipoTerritorio);
		when(territorioService.getTerritorioById("7051")).thenReturn(mockTerritorioVilla);
		
		// Fichero vacio
		MultipartFile multipartFileVacio = new MockMultipartFile("file", "queryFile.csv", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/consulta/upload")
				.file((MockMultipartFile) multipartFileVacio)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("consulta/upload"));
		
		// Extension erronea
		MultipartFile multipartFileExtError = new MockMultipartFile("file", "queryFile.txt", MediaType.TEXT_PLAIN_VALUE, 
		"0;Descripcion;Consulta1\n1;Nivel;M;Municipio\n2;AmbitoGeneral;M;Municipio\n3;AmbitoParticular;7051;Villanueva de Bogas [45193]\n4;TipoFuente;censoderecho\n5;CensoDerechoAnio;1900\n6;CensoHechoAnio; \n7;PadronAnio; \n".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/consulta/upload")
				.file((MockMultipartFile) multipartFileExtError)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("consulta/upload"));
		
		// Upload OK
		MultipartFile multipartFileOk = new MockMultipartFile("file", "queryFile.csv", MediaType.TEXT_PLAIN_VALUE,
		"0;Descripcion;Consulta1\n1;Nivel;M;Municipio\n2;AmbitoGeneral;M;Municipio\n3;AmbitoParticular;7051;Villanueva de Bogas [45193]\n4;TipoFuente;censoderecho\n5;CensoDerechoAnio;1900\n6;CensoHechoAnio; \n7;PadronAnio; \n".getBytes());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.multipart("/consulta/upload")
				.file((MockMultipartFile) multipartFileOk)
				.with(csrf()))
			.andExpect(redirectedUrl("/consulta/listar"));
			
	}
	
	// Test cargar
	@DisplayName("ConsultaController -> cargar")
	@Test
	void testCargar() throws Exception {
		// Cargar mas registros en la query de consulta OK
		ConsultaFormDTO consultaFormModelAttr = new ConsultaFormDTO();
		consultaFormModelAttr.setNivel("M");
		consultaFormModelAttr.setAmbitoGen("M");
		consultaFormModelAttr.setAmbitosPar(new String[] {"7051"});
		consultaFormModelAttr.setTpsFuente(new String[] {"censoderecho"});
		consultaFormModelAttr.setAnios(new String[] {"d1900"});
		consultaFormModelAttr.setCensoDerAnios(new String[] {"1900"});
		consultaFormModelAttr.setCensoHecAnios(new String[0]);
		consultaFormModelAttr.setPadronAnios(new String[0]);
		consultaFormModelAttr.setIdsTerritorioNivel(null);
		consultaFormModelAttr.setNumElementAct(1);
		consultaFormModelAttr.setNumMaxRegs(1000);
		consultaFormModelAttr.setNumTotRegs(1);
		
		ConsultaFormDescripcionDTO consultaFormDescModelAttr = new ConsultaFormDescripcionDTO();
		consultaFormDescModelAttr.setNivelDesc("Municipio");
		consultaFormDescModelAttr.setAmbitoGenDesc("Municipio");
		consultaFormDescModelAttr.setAmbitosParDesc(new String[] {"Villanueva de Bogas [45193]"});
		consultaFormDescModelAttr.setAmbitosParDescFormat("Villanueva de Bogas [45193]");
		consultaFormDescModelAttr.setCensoDerAniosDesc(new String[] {"1900"});
		consultaFormDescModelAttr.setCensoDerAniosDescFormat("1900");
		consultaFormDescModelAttr.setCensoHecAniosDesc(new String[0]);
		consultaFormDescModelAttr.setCensoHecAniosDescFormat("");
		consultaFormDescModelAttr.setPadronAniosDesc(new String[0]);
		consultaFormDescModelAttr.setPadronAniosDescFormat("");
		
		List<PoblacionDataDTO> poblacionDataAll = new ArrayList<PoblacionDataDTO>();
		PoblacionDataDTO poblacionDataVilla = new PoblacionDataDTO();
		poblacionDataVilla.setTipoTerritorio("Municipio");
		poblacionDataVilla.setNombre("Villanueva de Bogas");
		poblacionDataVilla.setCodigoOficial("45193");
		poblacionDataVilla.setTipoFuente("censo derecho");
		poblacionDataVilla.setAnio("1900");
		poblacionDataVilla.setPoblacion("830");
		poblacionDataAll.add(poblacionDataVilla);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("consultaForm", consultaFormModelAttr);
		session.setAttribute("consultaFormDesc", consultaFormDescModelAttr);
		session.setAttribute("poblacionDataAll", poblacionDataAll);
		request.setSession(session);
		
		this.mockMvc
    		.perform(MockMvcRequestBuilders.get("/consulta/cargar")
    				.session(session)
    				.with(csrf()))
    		.andExpect(redirectedUrlPattern("/consulta/listar*"));
		
		ConsultaFormDescripcionDTO test = (ConsultaFormDescripcionDTO) session.getAttribute("consultaFormDesc");
		assertEquals("Municipio", test.getNivelDesc());
		assertEquals("Municipio", test.getAmbitoGenDesc());
		assertEquals(1, test.getAmbitosParDesc().length);
		assertEquals("Villanueva de Bogas [45193]", test.getAmbitosParDescFormat());
		assertEquals(1, test.getCensoDerAniosDesc().length);
		assertEquals("1900", test.getCensoDerAniosDescFormat());
		assertEquals(0, test.getCensoHecAniosDesc().length);
		assertEquals("", test.getCensoHecAniosDescFormat());
		assertEquals(0, test.getPadronAniosDesc().length);
		assertEquals("", test.getPadronAniosDescFormat());
	}
	
	// Test gestionarModelos
	@DisplayName("ConsultaController -> gestionarModelos")
	@Test
	void testGestionarModelos() throws Exception {
		when(tipoTerritorioService.getTipoTerritorioAmbitoGenLst("M")).thenReturn(new ArrayList<TipoTerritorio>());
		when(territorioService.getTerritorioAmbitoParLst("M", "M")).thenReturn(new ArrayList<Territorio>());
		
		// Modelo OK - Censo derecho
		ConsultaFormDTO consultaFormRequest = new ConsultaFormDTO();
		consultaFormRequest.setNivel("M");
		consultaFormRequest.setAmbitoGen("M");
		consultaFormRequest.setAmbitosPar(new String[] {"7051"});
		consultaFormRequest.setTpsFuente(new String[] {"censoderecho"});
		consultaFormRequest.setAnios(new String[] {"d1900"});
		
		List<Fuente> fuenteCensoDerechoLst = new ArrayList<Fuente>();
		Fuente fuenteCenDer = new Fuente("censo", "derecho", "1842", null);
		fuenteCensoDerechoLst.add(fuenteCenDer);
		when(fuenteService.getFuenteCensoByTipoLst("derecho")).thenReturn(fuenteCensoDerechoLst);
		
		consultaController.gestionaModelos(consultaFormRequest, model, locale);
		
		// Modelo OK - Censo derecho
		consultaFormRequest.setTpsFuente(new String[] {"censohecho"});
		consultaFormRequest.setAnios(new String[] {"h1877"});
		
		List<Fuente> fuenteCensoHechoLst = new ArrayList<Fuente>();
		Fuente fuenteCenHec = new Fuente("censo", "hecho", "1877", null);
		fuenteCensoHechoLst.add(fuenteCenHec);
		when(fuenteService.getFuenteCensoByTipoLst("hecho")).thenReturn(fuenteCensoHechoLst);
		
		consultaController.gestionaModelos(consultaFormRequest, model, locale);
		
		// Modelo OK - Padron
		consultaFormRequest.setTpsFuente(new String[] {"padron"});
		consultaFormRequest.setAnios(new String[] {"p2000"});
		
		List<Fuente> fuentePadronLst = new ArrayList<Fuente>();
		Fuente fuentePadron = new Fuente("padrón", null, "2000", null);
		fuentePadronLst.add(fuentePadron);
		when(fuenteService.getFuentePadronLst()).thenReturn(fuentePadronLst);
		
		consultaController.gestionaModelos(consultaFormRequest, model, locale);
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
