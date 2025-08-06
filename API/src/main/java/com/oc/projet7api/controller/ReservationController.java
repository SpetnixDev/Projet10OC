package com.oc.projet7api.controller;

import com.oc.projet7api.model.dto.ReservationDTO;
import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.dto.ReservationResponseBatchDTO;
import com.oc.projet7api.service.ReservationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    public ReservationProjection createReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @GetMapping("/first")
    public List<ReservationResponseBatchDTO> getAllFirstReservations() {
        return reservationService.getAllFirstReservations();
    }

    @PutMapping("/rotate/{bookId}")
    public void rotateReservation(@PathVariable Long bookId) throws MessagingException {
        reservationService.rotateReservations(bookId);
    }
}