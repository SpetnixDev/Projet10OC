package com.oc.projet7batch.service;

import com.oc.projet7batch.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class ReservationService {
    @Autowired
    private WebClient webClient;

    public List<Reservation> getAllFirstReservations() {
        return webClient.get()
                .uri("/reservations/first")
                .retrieve()
                .bodyToFlux(Reservation.class)
                .collectList()
                .block();
    }

    public void rotate(Long bookId) {
        webClient.post()
                .uri("/reservations/rotate/" + bookId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
