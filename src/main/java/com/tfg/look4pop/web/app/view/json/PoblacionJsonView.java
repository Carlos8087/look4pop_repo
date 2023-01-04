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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

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
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		List<PoblacionDataDTO> poblacionDataLst = gson.fromJson((String) model.get("datos"), collectionType);
		
		String poblacionDataJsonString = gson.toJson(poblacionDataLst);
		
		PrintWriter out = response.getWriter();
		out.print(poblacionDataJsonString);
		out.flush();
	}
	
	public void exportJsonTest(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.renderMergedOutputModel(model, request, response);
	}

}
