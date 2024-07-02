package com.sopra.pflanzenkleinanzeigen.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {

    public ByteArrayInputStream generatePlantPdf(Plant plant) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Load image from classpath
            ClassPathResource imgFile = new ClassPathResource("static/" + plant.getImagePath());
            InputStream imgStream = imgFile.getInputStream();
            Image img = Image.getInstance(imgStream.readAllBytes());
            img.scaleToFit(250, 250);
            img.setAlignment(Element.ALIGN_CENTER);
            document.add(img);

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Plant Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add a table
            PdfPTable table = new PdfPTable(2); // 2 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set Column widths
            float[] columnWidths = {1f, 2f};
            table.setWidths(columnWidths);

            // Add table header
            PdfPCell cell1 = new PdfPCell(new Paragraph("Attribute"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Value"));
            table.addCell(cell1);
            table.addCell(cell2);

            // Add table rows
            table.addCell("Name");
            table.addCell(plant.getName());
            table.addCell("Price");
            table.addCell(plant.getPrice() + " €");
            table.addCell("Height");
            table.addCell(plant.getHeight() + " cm");
            table.addCell("Description");
            table.addCell(plant.getDescription());
            table.addCell("Pot Included");
            table.addCell(plant.isPotIncluded() ? "Yes" : "No");

            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
