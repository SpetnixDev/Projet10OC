package com.oc.projet7api.service;

import com.oc.projet7api.model.entity.Reservation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public void sendAvailableBookEmail(Reservation reservation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(reservation.getUser().getEmail());
        helper.setSubject("Livre disponible !");
        helper.setText(buildEmailContent(reservation), true);

        mailSender.send(message);
    }

    private String buildEmailContent(Reservation reservation) {
        return "<p>Bonjour " + reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName() + ",</p>" +
                "<p>Un exemplaire d'un livre que vous avez réservé est désormais disponible pour vous :</p>" +
                "<p><strong>" + reservation.getBook().getTitle() + "</strong></p>" +
                "<p>Vous pouvez venir emprunter cet exemplaire sous 48h à partir de la date de réception de ce mail. Sinon, il deviendra réservé à la prochaine personne sur la liste d'attente.</p>" +
                "<p>Cordialement,<br>Votre Bibliothèque</p>";
    }
}
