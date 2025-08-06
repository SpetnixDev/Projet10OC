package com.oc.projet7batch.service;

import com.oc.projet7batch.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.oc.projet7batch.model.Loan;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String email, String subject, String content, boolean html) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(content, html);

		mailSender.send(message);
	}
	
	public void sendReminderEmail(Loan loan) throws MessagingException {
		sendEmail(
			loan.getUserEmail(),
			"Livre non rendu !",
			buildLoanReminderEmailContent(loan),
			true
		);
	}

	private String buildLoanReminderEmailContent(Loan loan) {
		return "<p>Bonjour " + loan.getUserFirstName() + " " + loan.getUserLastName() + ",</p>" +
                "<p>Vous avez dépassé la date limite (" + loan.getReturnDate() + ") pour rendre le livre suivant :</p>" +
                "<p><strong>" + loan.getBookTitle() + "</strong></p>" +
                "<p>Merci de le retourner dès que possible.</p>" +
                "<p>Cordialement,<br>Votre Bibliothèque</p>";
	}
}
