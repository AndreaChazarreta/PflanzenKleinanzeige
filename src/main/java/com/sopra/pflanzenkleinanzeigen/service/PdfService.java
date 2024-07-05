package com.sopra.pflanzenkleinanzeigen.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
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
            ClassPathResource imgFile = new ClassPathResource("static/plant-images/logoPlantHeart.png");
            InputStream imgStream = imgFile.getInputStream();
            Image img = Image.getInstance(imgStream.readAllBytes());
            img.scaleToFit(50, 50);
            img.setAlignment(Element.ALIGN_LEFT);
            document.add(img);

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Care Tips for " + plant.getName(), titleFont);
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

            // Add table header with no borders
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Retrieve CareTip associated with the plant
            CareTip careTip = plant.getCareTip();

            // Add care tips to table
            addCareTipRow(table, "Plant Name", careTip.getPlantName());
            addCareTipRow(table, "Irrigation", careTip.getIrrigation());
            addCareTipRow(table, "Lighting Conditions", careTip.getLightingConditions());
            addCareTipRow(table, "Fertilization", careTip.getFertilization());
            addCareTipRow(table, "Repotting", careTip.getRepotting());
            addCareTipRow(table, "Temperature", careTip.getTemperature());
            addCareTipRow(table, "Other Tips", careTip.getOtherTips());
            addCareTipRow(table, "Planting", careTip.getPlanting());

            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void styleCell(PdfPCell cell) {
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(10f);
        cell.setBorderWidthBottom(1f);
        cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
    }

    private void addCareTipRow(PdfPTable table, String attribute, String value) {
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfPCell cell1 = new PdfPCell(new Paragraph(attribute, rowFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph(value, rowFont));
        styleCell(cell1);
        styleCell(cell2);
        table.addCell(cell1);
        table.addCell(cell2);
    }
}
