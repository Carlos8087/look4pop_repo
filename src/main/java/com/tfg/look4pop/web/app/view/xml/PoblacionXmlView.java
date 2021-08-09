package com.tfg.look4pop.web.app.view.xml;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;


/*
public class PoblacionXmlView extends MarshallingView {
	
	@Autowired
	public PoblacionXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Hay que dejar el model-vista completamente limpio...
		model.remove("tituloPagina");
		model.remove("consultaForm");
		model.remove("consultaFormDTO");
		model.remove("selectNiveles");
		model.remove("consultaFormDesc");
		model.remove("success");
		
		List<PoblacionDataDTO> poblacionDataLst = (List<PoblacionDataDTO>) model.get("poblacionData");
		model.remove("poblacionData"); // No lo queremos dentro del XML (de esta forma)
		
		// Lo unico que vamos a convertir a XML, segun la configuracion que hemos hecho, es el objeto 'PoblacionList' (Wrapper)
		// Añadimos al model-vista solo lo que nos interesa, la 'lista de poblacion'...
		model.put("poblacionList", new PoblacionList(poblacionDataLst)); // Debe estar definido mediante nuestro Wrapper 
		
		super.renderMergedOutputModel(model, request, response);
	}

}
*/

@Component("consulta/listar.xml")
public class PoblacionXmlView extends AbstractView {
	
	public PoblacionXmlView() {
		setContentType("application/xml");
	}
	
	// Genera un contenido que es descargable? -> SI (true)
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	} 

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Para asignar el nombre del fichero
		response.setHeader("Content-Disposition", "attachment; filename=\"poblacion_datos.xml\"");
		
		// Asignamos el 'tipo de contenido'
		response.setContentType(getContentType() + ";charset=UTF-8");
		
		//List<PoblacionDataDTO> poblacionDataLst = 
		//PoblacionList poblacionDataLst = new PoblacionList((List<PoblacionDataDTO>) model.get("poblacionData"));
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		PoblacionList poblacionDataLst = new PoblacionList((List<PoblacionDataDTO>) gson.fromJson((String) model.get("datos"), collectionType));
		
		
		
		
		/*
		// Configuracion del destino y formato de composicion del fichero
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		
		// Header del fichero
		String[] header = {"tipoTerritorio", "nombre", "codigoOficial", "tipoFuente", "subtipoFuente", "anio", "poblacion"}; // Los nombres definidos deben coincidir con los nombres de los atributos
		beanWriter.writeHeader(header);
		
		for (PoblacionDataDTO poblacionData : poblacionDataLst) {
			beanWriter.write(poblacionData, header);
		}
		
		beanWriter.close();
		*/
		
		XmlMapper xmlMapper = new XmlMapper();
		String poblacionDataXMLString = xmlMapper.writeValueAsString(poblacionDataLst);
		//byte[] customerJsonBytes = customerJsonString.getBytes();
		
		PrintWriter out = response.getWriter();
		//response.setContentType("application/json");
		//response.setCharacterEncoding("UTF-8");
		out.print(poblacionDataXMLString);
		out.flush();
		
		
		
		
		/*
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentLength(customerJsonBytes.length);
		response.getWriter().write(customerJsonString);
		response.getWriter().flush();
		*/
			
		/*
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=customers.json")
				.contentType(MediaType.APPLICATION_JSON)
				.contentLength(customerJsonBytes.length)
				.body(customerJsonBytes);
		*/
		
	}
	
}
