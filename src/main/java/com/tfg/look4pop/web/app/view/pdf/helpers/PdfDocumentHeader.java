package com.tfg.look4pop.web.app.view.pdf.helpers;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

public class PdfDocumentHeader extends PdfPageEventHelper {

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		Rectangle pagesize = document.getPageSize();
		
		Chunk imageESI = null, imageL4P = null;
		try {
			//imageESI = new Chunk(Image.getInstance("/images/esi-uclm.png"), 120, -50);
			//imageL4P = new Chunk(Image.getInstance("/images/logo.png"), 5, -67);
			imageESI = new Chunk(Image.getInstance("/app/pdfresources/esi-uclm-topdf.png"), 120, -50);
			imageL4P = new Chunk(Image.getInstance("/app/pdfresources/logo-topdf.png"), 5, -67);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Paragraph pESI = new Paragraph("");
	    pESI.add(imageESI);
	    document.add(pESI);
	    
	    Paragraph pL4P = new Paragraph("");
	    pL4P.add(imageL4P);
	    document.add(pL4P);
	    
		//String header = "LOOK4POP                                                                                                  Carlos Sánchez Martín";
	    String header = "Carlos Sánchez Martín";
		Font font = new Font(Font.HELVETICA, 12, Font.BOLDITALIC);
		Phrase headerPhrase = new Phrase(header, font);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, 
		        headerPhrase, 440,
	            pagesize.getTop() - 20,
	            0);
		LineSeparator ls = new LineSeparator();
		ls.setLineColor(Color.BLACK);
		ls.setPercentage(0.5f);
		Phrase headerLine = new Phrase(new Chunk(ls));
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, 
				headerLine, (pagesize.getLeft() + pagesize.getRight()) / 2,
	            pagesize.getTop() - 115,
	            0);
		
		
	}

}
