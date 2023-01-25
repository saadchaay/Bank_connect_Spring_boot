package com.bankconnect.helpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InvoiceGenerate {

    public void create(Facture facture) throws IOException {
        System.out.println("inside facture");
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        document.addPage(page);

        // Create a font
        PDFont font = PDType1Font.HELVETICA_BOLD;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Invoice");
        contentStream.endText();

        // Add the invoice number and date
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Invoice Number: "+facture.getRefBill());
        contentStream.newLineAtOffset(0, -25);
        contentStream.showText("Invoice Date: "+ formatter.format(LocalDate.now()));
        contentStream.endText();

        // Add the customer information
        contentStream.beginText();
        contentStream.newLineAtOffset(350, 750);
        contentStream.showText("Customer Name: "+facture.getAccount().getCustomer().getName());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 725);
        contentStream.showText("Customer Address: "+facture.getAccount().getCustomer().getAddress());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 700);
        contentStream.showText("Customer Phone: "+facture.getAccount().getCustomer().getPhone());
        contentStream.endText();

        // Add the invoice items
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 650);
        contentStream.showText("");
//        contentStream.newLineAtOffset(150, 0);
//        contentStream.showText("Quantity");
        contentStream.newLineAtOffset(125, 0);
        contentStream.showText("Price");
        contentStream.newLineAtOffset(175, 0);
        contentStream.showText("Total");
        contentStream.endText();

        contentStream.drawLine(50, 640, 550, 640);

        // Add the first invoice item
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 625);
        contentStream.showText("Product: "+facture.getType());
//        contentStream.newLineAtOffset(150, 0);
//        contentStream.showText("2");
        contentStream.newLineAtOffset(125, 0);
        contentStream.showText("DH "+facture.getAmount());
        contentStream.newLineAtOffset(175, 0);
        contentStream.showText("DH "+facture.getAmount());
        contentStream.endText();


        // Save the results and ensure that the document is properly closed:
        contentStream.close();
        document.save("invoice.pdf");
        document.close();
    }
}
