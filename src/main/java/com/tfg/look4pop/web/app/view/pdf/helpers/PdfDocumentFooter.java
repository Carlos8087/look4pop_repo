package com.tfg.look4pop.web.app.view.pdf.helpers;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfDocumentFooter extends  PdfPageEventHelper {

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle pagesize = document.getPageSize();
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.valueOf(writer.getPageNumber() - 1)), (pagesize.getLeft() + pagesize.getRight()) / 2,
	            pagesize.getBottom() + 25,
	            0);

	}

}
