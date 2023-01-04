package com.tfg.look4pop.web.app.view.csv;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

@Component("consulta/listar.csv")
public class PoblacionCsvView extends AbstractView {

	public PoblacionCsvView() {
		setContentType("text/csv");
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
		response.setHeader("Content-Disposition", "attachment; filename=\"poblacion_datos.csv\"");
		
		// Asignamos el 'tipo de contenido'
		response.setContentType(getContentType());
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		List<PoblacionDataDTO> poblacionDataLst = gson.fromJson((String) model.get("datos"), collectionType);
		
		// Configuracion del destino y formato de composicion del fichero
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		
		// Header del fichero
		String[] header = {"tipoTerritorio", "nombre", "codigoOficial", "tipoFuente", "anio", "poblacion"}; // Los nombres definidos deben coincidir con los nombres de los atributos
		beanWriter.writeHeader(header);
		
		for (PoblacionDataDTO poblacionData : poblacionDataLst) {
			beanWriter.write(poblacionData, header);
		}
		
		beanWriter.close();
	}
	
	public void exportCsvTest(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.renderMergedOutputModel(model, request, response);
	}

}
