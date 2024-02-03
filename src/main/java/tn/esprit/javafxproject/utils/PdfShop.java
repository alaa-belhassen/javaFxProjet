package tn.esprit.javafxproject.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.LigneDeCommande;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.DonServiceImpl;
import tn.esprit.javafxproject.services.LigneDeCommandeService;
import tn.esprit.javafxproject.services.ServiceAchat;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tn.esprit.javafxproject.BookingController.total;

public class PdfShop {

    static public int counter;
    String path="PdfAchat"+counter+".pdf";
    float threeCol = 190f;
    float twoCol = 285f;
    float twoCOl150 = twoCol+150f;
    float[] twoColumWidth = {twoCOl150,twoCol};
    float[] fullColumnWidth = {threeCol*3};
    float[] threeColumnWidth = {threeCol,threeCol,threeCol};

    public PdfShop(){
        counter++;
    }
    public void generatePdf() throws FileNotFoundException, SQLException {
        List<LigneDeCommande> Lignecommande = new ArrayList<LigneDeCommande>(getLignedeCommande());
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
        nestedtable.addCell(new Cell().add(new Paragraph("RBK25241")).setBorder(Border.NO_BORDER));
        nestedtable.addCell(new Cell().add(new Paragraph("Invoice Date")).setBold().setBorder(Border.NO_BORDER).setBold());
        nestedtable.addCell(new Cell().add(new Paragraph("7/4/2024")).setBorder(Border.NO_BORDER));
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
        twoColTable2.addCell(getCell10Left("CoddingError",false));
        twoColTable2.addCell(getCell10Left("Coding",false));
        document.add(twoColTable2);

        document.add(divider2.setMarginTop(12f));
        Paragraph title = new Paragraph("Products");
        document.add(title);
        document.add(getTableHeader("nomproduit","quantiter","prixcommande"));

        Table threeColTable2 = new Table(threeColumnWidth);


        for(LigneDeCommande lignecommande:Lignecommande) {

            threeColTable2.addCell(new Cell().add(new Paragraph(lignecommande.getProduit().getProductName())).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(new Paragraph(Integer.toString(lignecommande.getQuantityOrdred()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(new Paragraph(Double.toString(lignecommande.getPrice()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        }
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
    private List<LigneDeCommande> getLignedeCommande() throws SQLException {
        LigneDeCommandeService ligneDecommande = new LigneDeCommandeService();
        ServiceAchat serviceAchat = new ServiceAchat();

        return ligneDecommande.getMyList(serviceAchat.getLastId());
    }
}
