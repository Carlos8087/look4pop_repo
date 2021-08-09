package com.tfg.look4pop.web.app.view.xlsx;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

@Component("consulta/listar.xlsx")
public class PoblacionXlsxView extends AbstractXlsxView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Para asignar el nombre del fichero
		response.setHeader("Content-Disposition", "attachment; filename=\"poblacion_datos.xlsx\"");
		
		// List<PoblacionDataDTO> poblacionDataLst = (List<PoblacionDataDTO>) model.get("poblacionData");
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<PoblacionDataDTO>>(){}.getType();
		List<PoblacionDataDTO> poblacionDataLst = gson.fromJson((String) model.get("datos"), collectionType);
		
		// Creamos una hoja dentro del documento
		Sheet sheet = workbook.createSheet("Población Datos");
		
		// Traduccion de mensajes 
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		CellStyle tintroStyle = workbook.createCellStyle();
		Font fontIntro = workbook.createFont();
		fontIntro.setFontHeightInPoints((short) 12);
		fontIntro.setBold(true);
		tintroStyle.setFont(fontIntro);
		tintroStyle.setBorderBottom(BorderStyle.THIN);
		tintroStyle.setBorderTop(BorderStyle.THIN);
		tintroStyle.setBorderRight(BorderStyle.THIN);
		tintroStyle.setBorderLeft(BorderStyle.THIN);
		tintroStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		tintroStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Row row1 = sheet.createRow(1); // Segunda fila
		row1.createCell(1).setCellValue("LOOK4POP"); // Segunda columna
		row1.getCell(1).setCellStyle(tintroStyle);
		
		Row row2 = sheet.createRow(2); // Tercera fila
		row2.createCell(1).setCellValue("Carlos Sánchez Martín"); // Segunda columna
		row2.getCell(1).setCellStyle(tintroStyle);
		
		CellStyle tdescriptionStyle = workbook.createCellStyle();
		Font fontDescription = workbook.createFont();
		fontDescription.setFontHeightInPoints((short) 12);
		tdescriptionStyle.setFont(fontDescription);
		
		Row row5 = sheet.createRow(5); // Sexta fila
		row5.createCell(1).setCellValue(mensajes.getMessage("text.informes.intro")); // Segunda columna
		row5.getCell(1).setCellStyle(tdescriptionStyle);
		
		/*
		Row row = null;
		Cell cell = null;
		
		row = sheet.createRow(1); // Segunda fila
		cell = row.createCell(1); // Segunda columna
		//cell.setCellValue("Datos del cliente");
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
		
		row = sheet.createRow(1); // Segunda fila
		cell = row.createCell(0); // Primera columna
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2); // Tercera fila
		cell = row.createCell(0); // Primera columna
		cell.setCellValue(factura.getCliente().getEmail());
		
		// Otra forma mas directa para seguir creando filas / columnas...
		
		// La cuarta fila es una fila vacia
		//sheet.createRow(4).createCell(0).setCellValue("Datos de la factura"); // Quinta fila
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura")); // Quinta fila
		//sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId()); // Sexta fila
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId()); // Sexta fila
		//sheet.createRow(6).createCell(0).setCellValue("Descripcion: " + factura.getDescripcion()); // Septima fila
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion()); // Septima fila
		//sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt()); // Octava fila
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt()); // Octava fila
		
		*/
		
		CellStyle theaderStyle = workbook.createCellStyle();
		Font fontHeader = workbook.createFont();
		fontHeader.setFontHeightInPoints((short) 12);
		fontHeader.setBold(true);
		theaderStyle.setFont(fontHeader); 
		theaderStyle.setAlignment(HorizontalAlignment.CENTER);
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		//theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		HSSFWorkbook hwbHeader = new HSSFWorkbook();
		HSSFPalette paletteHeader = hwbHeader.getCustomPalette();
		HSSFColor colorHeader = paletteHeader.findSimilarColor(186, 214, 248);
		short palIndexHeader = colorHeader.getIndex();
		theaderStyle.setFillForegroundColor(palIndexHeader);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		hwbHeader.close();
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		Font fontBody = workbook.createFont();
		fontBody.setFontHeightInPoints((short) 12);
		tbodyStyle.setFont(fontBody); 
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		tbodyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Row header = sheet.createRow(8); // Segunda fila
		header.createCell(1).setCellValue(mensajes.getMessage("text.listar.tabla.tipoTerritorio")); // Segunda columna
		header.createCell(2).setCellValue(mensajes.getMessage("text.listar.tabla.nombre"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.listar.tabla.codigoOficial"));
		header.createCell(4).setCellValue(mensajes.getMessage("text.listar.tabla.tipoFuente"));
		//header.createCell(5).setCellValue(mensajes.getMessage("text.listar.tabla.subtipoFuente"));
		header.createCell(5).setCellValue(mensajes.getMessage("text.listar.tabla.anio"));
		header.createCell(6).setCellValue(mensajes.getMessage("text.listar.tabla.poblacion"));		
		
		// Aplicando estilos...
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		header.getCell(5).setCellStyle(theaderStyle);
		header.getCell(6).setCellStyle(theaderStyle);
		//header.getCell(7).setCellStyle(theaderStyle);
		
		sheet.setColumnWidth(1, 22 * 256);
		sheet.setColumnWidth(2, 45 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 12 * 256);
		sheet.setColumnWidth(6, 15 * 256);
		//sheet.setColumnWidth(7, 18 * 256);
		//sheet.setColumnWidth(9, 22 * 256);
		
		Integer numRegistros = poblacionDataLst.size();
		int rownum = 9;
		
		if (numRegistros > 0) {
			
			for (PoblacionDataDTO poblacionData : poblacionDataLst) {
		
				Row filaDetalle = sheet.createRow(rownum++);
			
				filaDetalle.createCell(1).setCellValue(poblacionData.getTipoTerritorio());
				filaDetalle.createCell(2).setCellValue(poblacionData.getNombre());
				filaDetalle.createCell(3).setCellValue(poblacionData.getCodigoOficial());
				filaDetalle.createCell(4).setCellValue(poblacionData.getTipoFuente());
				//filaDetalle.createCell(5).setCellValue(poblacionData.getSubtipoFuente());
				filaDetalle.createCell(5).setCellValue(poblacionData.getAnio());
				filaDetalle.createCell(6).setCellValue(poblacionData.getPoblacion());
				
				filaDetalle.getCell(1).setCellStyle(tbodyStyle);
				filaDetalle.getCell(2).setCellStyle(tbodyStyle);
				filaDetalle.getCell(3).setCellStyle(tbodyStyle);
				filaDetalle.getCell(4).setCellStyle(tbodyStyle);
				filaDetalle.getCell(5).setCellStyle(tbodyStyle);
				filaDetalle.getCell(6).setCellStyle(tbodyStyle);
				//filaDetalle.getCell(7).setCellStyle(tbodyStyle);
			}
			
		} else {
			
		}
		
	}
	
}
