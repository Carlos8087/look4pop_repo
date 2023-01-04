package com.tfg.look4pop.web.app.view.xml;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

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
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		PoblacionList poblacionDataLst = new PoblacionList((List<PoblacionDataDTO>) gson.fromJson((String) model.get("datos"), collectionType));
		
		XmlMapper xmlMapper = new XmlMapper();
		String poblacionDataXMLString = xmlMapper.writeValueAsString(poblacionDataLst);
		
		PrintWriter out = response.getWriter();
		out.print(poblacionDataXMLString);
		out.flush();
	}
	
	public void exportXmlTest(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.renderMergedOutputModel(model, request, response);
	}
	
}
