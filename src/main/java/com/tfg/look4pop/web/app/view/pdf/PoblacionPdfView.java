package com.tfg.look4pop.web.app.view.pdf;

import java.awt.Color;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;
import com.tfg.look4pop.web.app.view.pdf.helpers.PdfDocumentFooter;
import com.tfg.look4pop.web.app.view.pdf.helpers.PdfDocumentHeader;

@Component("consulta/listar.pdf")
public class PoblacionPdfView extends AbstractPdfView {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Header del fichero (nombre, ...)
		response.setHeader("Content-Disposition", "attachment; filename=\"poblacion_datos.pdf\"");
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		List<PoblacionDataDTO> poblacionDataLst = gson.fromJson((String) model.get("datos"), collectionType);
		
		
		
		
		
		//List<PoblacionDataDTO> pd = gson.fromJson((String)model.get("datos"), PoblacionDataDTO.class);
		
		//List<PoblacionDataDTO> poblacionDataLst = (List<PoblacionDataDTO>) model.get("poblacionData");
		//List<PoblacionDataDTO> poblacionDataLst = new ArrayList<PoblacionDataDTO>();
		//poblacionDataLst.add(pd);
		
		Locale locale = localeResolver.resolveLocale(request);		
		
		Integer numRegistros = poblacionDataLst.size();
		Integer cont = 0;
		PdfPTable table = null;
		Paragraph p = null;
		
		// Obtenemos una instancia de nuestro manejador de eventos
		PdfDocumentHeader header = new PdfDocumentHeader();
		PdfDocumentFooter footer = new PdfDocumentFooter();
		   
		// Asignamos el manejador de eventos al escritor.
		writer.setPageEvent(header);
		writer.setPageEvent(footer);
		
		// Abrimos una nueva pagina
		document.open();
		
		Font font = new Font(Font.HELVETICA, 10);
		p = new Paragraph("\r\n\r\n\r\n\r\n\r\n" + messageSource.getMessage("text.informes.intro", null, locale));
		
		document.add(p);
		
		// Tabla (Cabecera / Detalle)
		table = createTable();
		printHeader(table, locale);
		
		if (numRegistros > 0) {
			
			for (PoblacionDataDTO poblacionData : poblacionDataLst) {
				
				if (cont == 30) {
					cont = 0;
					document.add(table);
					document.newPage();
					p = new Paragraph("\r\n\r\n\r\n\r\n\r\n\r\n\r\n", font);
					document.add(p);
					table = createTable();
					printHeader(table, locale);
				}
				
				if (cont < 30) {
					printDetail(table, locale, poblacionData);
					cont++;
				} 
				
			}
			
			document.add(table);
		
		} else {
			
		}
		
		document.close();
		
	}
	
	private PdfPTable createTable() {
		
		PdfPTable table = new PdfPTable(6); // 6 columnas
		table.setWidthPercentage(100); // % de anchura
				
		float[] widths = new float[] { 80f, 135f, 60f, 60f, 30f, 55f }; // Ancho de cada columna
		table.setWidths(widths);
		table.setSpacingBefore(30); // Espacio antes de la tabla. Margen
		table.setSpacingAfter(30); // Espacio despues de la tabla. Margen
		
		return table;
	}
	
	private void printHeader(PdfPTable table, Locale locale) {
		
		Phrase phrase = null;
		
		// Cabecera. Estilos de celda
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new Color(186, 214, 248));
		cell.setPaddingTop(2f);
		cell.setPaddingBottom(4f);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		Font font = new Font(Font.HELVETICA, 10, Font.BOLD);
		
		// Tipo territorio
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.tipoTerritorio", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
		// Nombre
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.nombre", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
		// Codigo oficial
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.codigoOficial", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
		// Tipo fuente
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.tipoFuente", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
		/*
		// Subtipo fuente
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.subtipoFuente", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		*/
		
		// Anio
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.anio", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
		// Poblacion
		phrase = new Phrase(messageSource.getMessage("text.listar.tabla.poblacion", null, locale), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
	}
	
	private void printDetail(PdfPTable table, Locale locale, PoblacionDataDTO poblacionData) {
		
		Phrase phrase = null;
		
		// Detalle. Estilos de celda
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new Color(244, 243, 243));
		cell.setPaddingTop(2f);
		cell.setPaddingBottom(4f);
		Font font = new Font(Font.HELVETICA, 10);
		
		// Tipo territorio
		phrase = new Phrase(poblacionData.getTipoTerritorio(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
				
		// Nombre
		phrase = new Phrase(poblacionData.getNombre(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
				
		// Codigo oficial
		phrase = new Phrase(poblacionData.getCodigoOficial(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
			
		// Tipo fuente
		phrase = new Phrase(poblacionData.getTipoFuente(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
				
		/*
		// Subtipo fuente
		phrase = new Phrase(poblacionData.getSubtipoFuente(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		*/
				
		// Anio
		phrase = new Phrase(poblacionData.getAnio(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
			
		// Poblacion
		phrase = new Phrase(poblacionData.getPoblacion(), font);
		cell.setPhrase(phrase);
		table.addCell(cell);
		
	}

}
