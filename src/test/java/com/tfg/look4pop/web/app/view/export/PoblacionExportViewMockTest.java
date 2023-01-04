package com.tfg.look4pop.web.app.view.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.tfg.look4pop.web.app.view.csv.PoblacionCsvView;
import com.tfg.look4pop.web.app.view.json.PoblacionJsonView;
import com.tfg.look4pop.web.app.view.pdf.PoblacionPdfView;
import com.tfg.look4pop.web.app.view.xlsx.PoblacionXlsxView;
import com.tfg.look4pop.web.app.view.xml.PoblacionXmlView;

@SpringBootTest
public class PoblacionExportViewMockTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@BeforeEach
	void setUp() throws Exception {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}
	
	// Test exportPdf
	@DisplayName("Pdf export -> exportPdf")
	@Test
	void testExportPdf() throws Exception {
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("datos", "[{'tipoTerritorio':'Municipio','nombre':'Villanueva de Bogas','codigoOficial':'45193','tipoFuente':'censo derecho','anio':'1900','poblacion':'830'}]");
		
		PoblacionPdfView exportPdf = new PoblacionPdfView();
		exportPdf.exportPdfTest(model, document, writer, request, response, messageSource, localeResolver);
		
		assertEquals(true, response.getHeader("Content-Disposition").equals("attachment; filename=\"poblacion_datos.pdf\""));
	}
	
	// Test exportExcel
	@DisplayName("Excel export -> exportExcel")
	@Test
	void testExportExcel() throws Exception {
		Workbook workbook = new HSSFWorkbook(); 
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("datos", "[{'tipoTerritorio':'Municipio','nombre':'Villanueva de Bogas','codigoOficial':'45193','tipoFuente':'censo derecho','anio':'1900','poblacion':'830'}]");
		
		PoblacionXlsxView exportExcel = new PoblacionXlsxView();
		exportExcel.exportExcelTest(model, workbook, request, response, context);
		
		assertEquals(true, response.getHeader("Content-Disposition").equals("attachment; filename=\"poblacion_datos.xlsx\""));
	}
	
	// Test exportCsv
	@DisplayName("Csv export -> exportCsv")
	@Test
	void testExportCsv() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("datos", "[{'tipoTerritorio':'Municipio','nombre':'Villanueva de Bogas','codigoOficial':'45193','tipoFuente':'censo derecho','anio':'1900','poblacion':'830'}]");
		
		PoblacionCsvView exportCsv = new PoblacionCsvView();
		exportCsv.exportCsvTest(model, request, response);
		
		assertEquals(true, response.getHeader("Content-Disposition").equals("attachment; filename=\"poblacion_datos.csv\""));
	}
	
	// Test exportXml
	@DisplayName("Xml export -> exportXml")
	@Test
	void testExportXml() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("datos", "[{'tipoTerritorio':'Municipio','nombre':'Villanueva de Bogas','codigoOficial':'45193','tipoFuente':'censo derecho','anio':'1900','poblacion':'830'}]");
		
		PoblacionXmlView exportXml = new PoblacionXmlView();
		exportXml.exportXmlTest(model, request, response);
		
		assertEquals(true, response.getHeader("Content-Disposition").equals("attachment; filename=\"poblacion_datos.xml\""));
	}
	
	// Test exportJson
	@DisplayName("Json export -> exportJson")
	@Test
	void testExportJson() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("datos", "[{'tipoTerritorio':'Municipio','nombre':'Villanueva de Bogas','codigoOficial':'45193','tipoFuente':'censo derecho','anio':'1900','poblacion':'830'}]");
		
		PoblacionJsonView exportJson = new PoblacionJsonView();
		exportJson.exportJsonTest(model, request, response);
		
		assertEquals(true, response.getHeader("Content-Disposition").equals("attachment; filename=\"poblacion_datos.json\""));
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
