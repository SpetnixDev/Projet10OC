package com.oc.projet7app.service;

import com.oc.projet7app.model.Reservation;
import com.oc.projet7app.model.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;

@Service
public class ReservationService {
    @Autowired
    private WebClient webClient;

    public Mono<Reservation> createReservation(Long userId, Long bookId) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setUserId(userId);
        reservationDTO.setBookId(bookId);

        return webClient.post()
                .uri("/reservations/create")
                .bodyValue(reservationDTO)
                .retrieve()
                .bodyToMono(Reservation.class);
    }

    public Mono<Void> cancelReservation(Long id) {
        return webClient.delete()
                .uri("/reservations/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Boolean> userHasActiveReservation(Long userId, Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/reservations/active")
                        .queryParam("userId", userId)
                        .queryParam("bookId", id)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
