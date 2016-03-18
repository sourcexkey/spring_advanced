package com.epam.cdp.web.view.pdf;

import com.epam.cdp.model.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CustomPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Ticket> tickets = (List<Ticket>) model.get("tickets");

        Table table = new Table(5);
        table.addCell("Id");
        table.addCell("UserId");
        table.addCell("EventId");
        table.addCell("Category");
        table.addCell("Place");

        for (Ticket ticket : tickets) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getUserId()));
            table.addCell(String.valueOf(ticket.getEventId()));
            table.addCell(String.valueOf(ticket.getCategory()));
            table.addCell(String.valueOf(ticket.getPlace()));
        }
        document.add(table);
    }
}