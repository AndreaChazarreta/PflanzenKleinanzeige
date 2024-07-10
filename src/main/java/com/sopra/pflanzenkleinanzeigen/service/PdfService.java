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

            ClassPathResource imgFile = new ClassPathResource("static/plant-images/logoPlantHeart.png");
            InputStream imgStream = imgFile.getInputStream();
            Image img = Image.getInstance(imgStream.readAllBytes());
            img.scaleToFit(50, 50);
            img.setAlignment(Element.ALIGN_LEFT);

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Pflegehinweise für " + plant.getName(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            PdfPTable titleTable = new PdfPTable(2);
            titleTable.setWidthPercentage(100);
            float[] titleTableWidths = {1f, 4f}; // Adjust the widths as needed
            titleTable.setWidths(titleTableWidths);

            PdfPCell imgCell = new PdfPCell(img);
            imgCell.setBorder(Rectangle.NO_BORDER);
            imgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell titleCell = new PdfPCell(title);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            titleTable.addCell(imgCell);
            titleTable.addCell(titleCell);

            document.add(titleTable);

            PdfPTable table = new PdfPTable(2); // 2 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 2f};
            table.setWidths(columnWidths);

            CareTip careTip = plant.getCareTip();

            addCareTipRow(table, "Bewässerung", careTip.getIrrigation(), generateIrrigationDescription(careTip.getIrrigation()));
            addCareTipRow(table, "Düngung", careTip.getFertilization(), generateFertilizationDescription(careTip.getFertilization()));
            addCareTipRow(table, "Lichtverhältnisse", careTip.getLightingConditions(), generateLightingDescription(careTip.getLightingConditions()));
            addCareTipRow(table, "Temperatur", careTip.getTemperature(), careTip.getTemperature() + " °C");
            addCareTipRow(table, "Pflanzung", careTip.getPlanting(), careTip.getPlanting());
            addCareTipRow(table, "Umtopfen", careTip.getRepotting(), careTip.getRepotting());
            addCareTipRow(table, "Weitere Tipps", careTip.getOtherTips(), careTip.getOtherTips());

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

    private void addCareTipRow(PdfPTable table, String attribute, String value, String description) {
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfPCell cell1 = new PdfPCell(new Paragraph(attribute, rowFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph(description, rowFont));
        styleCell(cell1);
        styleCell(cell2);
        table.addCell(cell1);
        table.addCell(cell2);
    }

    private String generateIrrigationDescription(String irrigation) {
        switch (irrigation) {
            case "Wenig":
                return "Die Pflanze braucht wenig Wasser, ungefähr einmal im Monat gießen";
            case "Mittel":
                return "Die Pflanze sollte einmal die Woche gegossen werden";
            case "Viel":
                return "Die Pflanze muss täglich gegossen werden";
            default:
                return irrigation;
        }
    }

    private String generateFertilizationDescription(String fertilization) {
        switch (fertilization) {
            case "Wenig":
                return "Die Pflanze braucht keinen Dünger";
            case "Mittel":
                return "Die Pflanze sollte einmal im Monat Dünger bekommen";
            case "Viel":
                return "Die Pflanze sollte einmal die Woche gedüngt werden";
            default:
                return fertilization;
        }
    }

    private String generateLightingDescription(String lightingConditions) {
        switch (lightingConditions) {
            case "Wenig":
                return "Die Pflanze kann im Schatten stehen";
            case "Mittel":
                return "Die Pflanze sollte im Halbschatten stehen";
            case "Viel":
                return "Die Pflanze braucht viel Sonne";
            default:
                return lightingConditions;
        }
    }
}
