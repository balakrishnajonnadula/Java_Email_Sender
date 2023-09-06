package com.email.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	public static String generatePdf() {
		String pdfFilePath = null;
		try {
			// Create a unique name for the PDF file based on the current timestamp
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String uniqueFileName = dateFormat.format(new Date()) + ".pdf";
			pdfFilePath = uniqueFileName;
			// Create a new Document
			Document document = new Document();

			// Create a PdfWriter to write the document to a file
			PdfWriter.getInstance(document, new FileOutputStream("E:/" + pdfFilePath));

			// Open the document for writing
			document.open();

			// Add content to the document
			document.add(new Paragraph("Hello, iText!"));

			// Close the document
			document.close();

			System.out.println("PDF created successfully.");
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

		return pdfFilePath;
	}
}
