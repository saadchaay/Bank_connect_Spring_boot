package com.bankconnect.helpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class InvoiceGenerate {

    public static void create() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
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
        contentStream.showText("Invoice Number: 123456");
        contentStream.newLineAtOffset(0, -25);
        contentStream.showText("Invoice Date: 01/01/2022");
        contentStream.endText();

        // Add the customer information
        contentStream.beginText();
        contentStream.newLineAtOffset(350, 750);
        contentStream.showText("Customer Name: John Doe");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 725);
        contentStream.showText("Customer Address: 123 Main Street");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 700);
        contentStream.showText("Customer Phone: 555-555-5555");
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
        contentStream.showText("Product A");
//        contentStream.newLineAtOffset(150, 0);
//        contentStream.showText("2");
        contentStream.newLineAtOffset(125, 0);
        contentStream.showText("$50.00");
        contentStream.newLineAtOffset(175, 0);
        contentStream.showText("$100.00");
        contentStream.endText();


        // Save the results and ensure that the document is properly closed:
        contentStream.close();
        document.save("invoice.pdf");
        document.close();
    }
}
