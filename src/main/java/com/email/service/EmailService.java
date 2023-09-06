package com.email.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.email.pdf.PdfGenerator;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		String generatePdf = PdfGenerator.generatePdf(); // Make sure to generate the PDF using PdfGenerator

		// Generate the PDF file
		String pdfFilePath = "E:/" + generatePdf; // Replace with the actual path

		// Create a multipart message
		Multipart multipart = new MimeMultipart();

		// Create a text part for the email body
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText("Please find the attached file.");
		multipart.addBodyPart(textPart);

		// Create a part for the file attachment
		MimeBodyPart filePart = new MimeBodyPart();
		DataSource source = new FileDataSource(pdfFilePath);
		filePart.setDataHandler(new DataHandler(source));
		filePart.setFileName("attachment.pdf"); // Set the desired file name
		multipart.addBodyPart(filePart);

		// Set the multipart message as the content of the email
		message.setContent(multipart);

		javaMailSender.send(message);
		
		System.out.println(pdfFilePath);

		// After sending, delete the attachment file
		File attachmentFile = new File(pdfFilePath);
		if (attachmentFile.exists()) {
			boolean deleted = attachmentFile.delete();
			if (deleted) {
				System.out.println("Attachment file deleted successfully.");
			} else {
				System.err.println("Failed to delete attachment file.");
			}
		}
	}
}
