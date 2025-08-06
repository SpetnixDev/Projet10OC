package com.oc.projet7batch.service;

import com.oc.projet7batch.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReservationRotationBatch {
    @Autowired
    private ReservationService reservationService;

    @Scheduled(cron = "0 */15 * * * ?")
    public void sendOverdueReminders() {
        List<Reservation> reservations = reservationService.getAllFirstReservations();

        for (Reservation reservation : reservations) {
            if (reservation.getNotified() != null) {
                if (Instant.now().isAfter(reservation.getNotified().plusSeconds(60 * 60 * 24 * 2))) {
                    reservationService.rotate(reservation.getBookId());
                }
            }
        }
    }
}
