package tn.esprit.javafxproject.utils;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.services.ReservationServiceImpl;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PdfLoaderReservation {
    static int counter;
    String path="reservation n"+counter+".pdf";
    float threeCol = 190f;
    float twoCol = 285f;
    float twoCOl150 = twoCol+150f;
    float[] twoColumWidth = {twoCOl150,twoCol};
    float[] fullColumnWidth = {threeCol*3};
    float[] threeColumnWidth = {threeCol,threeCol,threeCol};
    Reserver object;
    public PdfLoaderReservation(){
        counter ++;
    }
    public PdfLoaderReservation(Reserver obj){
        this.object=obj;
    }


    public void generatePdf(Reserver reserver) throws FileNotFoundException, SQLException {
        //List<Reserver> reservers = new ArrayList<Reserver>(getDons());
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        Table table = new Table(twoColumWidth);
        Cell cell1 = new Cell().add(new Paragraph("Invoice")).setBorder(Border.NO_BORDER).setBold().setFontSize(20f);
        table.addCell(cell1);
        float[] nestedtableCol = {twoCOl150/2,twoCol/2};
        Paragraph onesp = new Paragraph("\n");
        Table nestedtable = new Table(nestedtableCol);
        nestedtable.addCell(new Cell().add(new Paragraph("Invoice No.")).setBold().setBorder(Border.NO_BORDER).setBold());
        nestedtable.addCell(new Cell().add(new Paragraph(String.valueOf(reserver.getEvenement().getIdEvenement()))).setBorder(Border.NO_BORDER));
        nestedtable.addCell(new Cell().add(new Paragraph("Invoice Date")).setBold().setBorder(Border.NO_BORDER).setBold());
        nestedtable.addCell(new Cell().add(new Paragraph(LocalDate.now().toString())).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedtable).setBorder(Border.NO_BORDER));
        DeviceRgb grayColor = new DeviceRgb(128, 128, 128);

        Border border2 = new SolidBorder(grayColor,0.5f);
        Table divider2  = new Table(fullColumnWidth);
        divider2.setBorder(border2);

        document.add(table);
        document.add(onesp);
        document.add(divider2);
        document.add(onesp);

        Table twoColTable = new Table(twoColumWidth);
        twoColTable.addCell(new Cell().add(new Paragraph("Billing information")).setBold().setBorder(Border.NO_BORDER).setBold());
        twoColTable.addCell(new Cell().add(new Paragraph("Shipping Information")).setBold().setBorder(Border.NO_BORDER).setBold());
        document.add(twoColTable.setMarginBottom(8f));
        Table twoColTable2 = new Table(twoColumWidth);
        twoColTable2.addCell(getCell10Left("company",true));
        twoColTable2.addCell(getCell10Left("Name",true));
        twoColTable2.addCell(getCell10Left("JavaGeeks Application ",false));
        twoColTable2.addCell(getCell10Left("By Sarah special thanks Alaa",false));
        document.add(twoColTable2);

        document.add(divider2.setMarginTop(12f));
        Paragraph title = new Paragraph("Products");
        document.add(title);
        document.add(getTableHeader("Nom evenement","Quantite","prix total"));

        Table threeColTable2 = new Table(threeColumnWidth);

        double total = 0D;
       // for(Reserver reserver:reservers) {
            total+=reserver.getPrix_total();
            threeColTable2.addCell(new Cell().add(new Paragraph(reserver.getEvenement().getLibelle())).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(new Paragraph(String.valueOf(reserver.getPrix_total()/reserver.getEvenement().getPrix()))).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(new Paragraph(Float.toString(reserver.getEvenement().getPrix()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
      //  }
        document.add(threeColTable2.setMarginBottom(20f));
        float[] onetwo= {threeCol+125f,threeCol*2};
        Table threeColTable3 =new Table(onetwo);
        threeColTable3.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add(divider2).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        document.add(threeColTable3);
        Table threeColTable4 =new Table(threeColumnWidth);
        threeColTable4.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable4.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(new Paragraph(String.valueOf(total))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);
        document.close();

    }
    private Table getTableHeader(String title1,String title2,String title3){
        DeviceRgb grayColor = new DeviceRgb(128, 128, 128);
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(grayColor,0.7f);
        DeviceRgb whiteColor = new DeviceRgb(255, 255, 255);
        threeColTable1.addCell(new Cell().add(new Paragraph(title1)).setBold().setFontColor(whiteColor).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph(title2)).setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(whiteColor).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph(title3)).setTextAlignment(TextAlignment.LEFT).setBold().setFontColor(whiteColor).setBorder(Border.NO_BORDER));
        return threeColTable1;
    }
    static Cell getCell10Left(String textValue,Boolean isBold){
        Cell myCell = new Cell().add(new Paragraph(textValue)).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold?myCell.setBold():myCell;
    };
    private List<Reserver> getDons() throws SQLException {
        ReservationServiceImpl reservationService = new ReservationServiceImpl();
        return reservationService.getAll();
    }
}
