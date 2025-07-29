package com.oc.projet7batch.service;

import com.oc.projet7batch.model.Loan;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRotationBatch {
    @Autowired
    private LoanService loanService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 * * * * ?")
    public void sendOverdueReminders() {
        List<Loan> loans = loanService.getOverdueLoans();

        System.out.println("Starting daily batch...");

        for (Loan loan : loans) {
            try {
                emailService.sendReminderEmail(loan);
            } catch (MessagingException e) {
                System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            }
        }
    }
}
