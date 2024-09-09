package com.sys.myapp.util;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.sys.myapp.modelo.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/Clients/Constancia")
public class ConstanciaClients extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Reserva reserva= (Reserva) model.get("reserva");
		
		document.setPageSize(PageSize.LETTER);
		document.open();
		PdfPTable tablaTi=new PdfPTable(1);
		PdfPCell celda=null;
		PdfPTable tablasub=new PdfPTable(1);
		PdfPCell celdas=null;
		PdfPTable tablapie=new PdfPTable(1);
		PdfPCell pie=null;
		
		com.lowagie.text.Font fuenteTit = FontFactory.getFont("Helvetica",18,Color.BLUE);
		com.lowagie.text.Font fuentesub = FontFactory.getFont("Helvetica",14,Color.BLUE);
		
		celda = new PdfPCell(new Phrase("Constancia de Reserva - Hotel Central Chiclayo",fuenteTit));	
		celdas = new PdfPCell(new Phrase("'3 Estrellas en el Centro de Chiclayo'",fuentesub));
		
		celda.setBorder(0);
		celdas.setBorder(0);
		celda.setBackgroundColor(new Color(40,190,138));
		celdas.setBackgroundColor(new Color(40,190,138));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdas.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celdas.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPaddingTop(15);
		celda.setPaddingBottom(3);
		celdas.setPadding(10);
		
		tablaTi.addCell(celda);		
		tablasub.addCell(celdas);
		tablasub.setSpacingAfter(30);
		
		PdfPTable tabla1=new PdfPTable(4);
		
		tabla1.addCell("Nª de Reserva");
		tabla1.addCell("Estado");
		tabla1.addCell("Fecha de Reserva");
		tabla1.addCell("Costo de Alojamiento");				
		tabla1.addCell(reserva.getIdreserva().toString());
		tabla1.addCell(reserva.getEstado());
		tabla1.addCell(reserva.getFecha_reserva().toString());
		tabla1.addCell(reserva.getCosto_alojamiento().toString());
		
		tabla1.setSpacingAfter(30);
		
		PdfPTable tabla2=new PdfPTable(3);
		tabla2.addCell("Fecha de Llegada");
		tabla2.addCell("Fecha de Salida");
		tabla2.addCell("Id de Usuario");				
		tabla2.addCell(reserva.getFecha_ingresa().toString());		
		tabla2.addCell(reserva.getFecha_salida().toString());
		tabla2.addCell("Reservado por Cliente");		
		tabla2.setSpacingAfter(30);
		
		PdfPTable tabla3=new PdfPTable(4);
		tabla3.addCell("Piso");
		tabla3.addCell("Habitación");
		tabla3.addCell("Tipo de Habitación");	
		tabla3.addCell("Descripción");
		tabla3.addCell(reserva.getHabitacion().getPiso());		
		tabla3.addCell(reserva.getHabitacion().getNumero());
		tabla3.addCell(reserva.getHabitacion().getTipo_habitacion());
		tabla3.addCell(reserva.getHabitacion().getDescripcion());	
		tabla3.setSpacingAfter(30);
		
		PdfPTable tabla4=new PdfPTable(4);
		tabla4.addCell("Código de Cliente");
		tabla4.addCell("Tipo de Documento");
		tabla4.addCell("Número de Documento");	
		tabla4.addCell("Nombre de Cliente");
		tabla4.addCell(reserva.getCliente().getIdpersona().toString());	
		tabla4.addCell(reserva.getCliente().getPersona().getTipo_documento());
		tabla4.addCell(reserva.getCliente().getPersona().getNum_documento());
		tabla4.addCell(reserva.getCliente().getPersona().getNombreCompleto());
		tabla4.setSpacingAfter(5);		
		
		
		pie = new PdfPCell(new Phrase("'Todos los Derechos Reservados - Chiclayo'",fuentesub));
		pie.setBorder(0);
		pie.setBackgroundColor(new Color(40,120,138));		
		pie.setHorizontalAlignment(Element.ALIGN_CENTER);		
		pie.setVerticalAlignment(Element.ALIGN_CENTER);		
		pie.setPaddingTop(5);
		pie.setPaddingBottom(3);
		pie.setPadding(10);
		tablapie.addCell(pie);
		
		document.add(tablaTi);
		document.add(tablasub);
		document.add(tabla1);
		document.add(tabla2);
		document.add(tabla3);
		document.add(tabla4);
		document.add(tablapie);
		
	}

}
