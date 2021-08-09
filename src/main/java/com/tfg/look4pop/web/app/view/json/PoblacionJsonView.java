package com.tfg.look4pop.web.app.view.json;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;


/*
public class PoblacionJsonView extends MappingJackson2JsonView {

	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		model.remove("tituloPagina");
		model.remove("consultaForm");
		model.remove("consultaFormDTO");
		model.remove("selectNiveles");
		model.remove("consultaFormDesc");
		model.remove("success");
		model.remove("clsAdmin");
		model.remove("clsConsultas");
		model.remove("clsConsuFile");
		model.remove("clsConsuForm");
		model.remove("clsInicio");
		
		List<PoblacionDataDTO> poblacionDataLst = (List<PoblacionDataDTO>) model.get("poblacionData");
		model.remove("poblacionData");
		
		model.put("poblacionList", poblacionDataLst);
		
		return super.filterModel(model);
	}

}
*/

@Component("consulta/listar.json")
public class PoblacionJsonView extends AbstractView {
	
	public PoblacionJsonView() {
		setContentType("application/json");
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
		response.setHeader("Content-Disposition", "attachment; filename=\"poblacion_datos.json\"");
		
		// Asignamos el 'tipo de contenido'
		response.setContentType(getContentType() + ";charset=UTF-8");
		
		//List<PoblacionDataDTO> poblacionDataLst = (List<PoblacionDataDTO>) model.get("poblacionData");
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		List<PoblacionDataDTO> poblacionDataLst = gson.fromJson((String) model.get("datos"), collectionType);
		
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
		
		//Gson gson = new Gson();
		String poblacionDataJsonString = gson.toJson(poblacionDataLst);
		//byte[] customerJsonBytes = customerJsonString.getBytes();
		
		PrintWriter out = response.getWriter();
		//response.setContentType("application/json");
		//response.setCharacterEncoding("UTF-8");
		out.print(poblacionDataJsonString);
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
