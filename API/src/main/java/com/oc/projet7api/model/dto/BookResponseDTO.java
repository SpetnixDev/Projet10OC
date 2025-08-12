package com.oc.projet7api.model.dto;

import com.oc.projet7api.model.entity.Reservation;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookResponseDTO {
    private Long id;

    private String title;
    private String author;

    private LocalDate releaseDate;

    private int totalCopies;
    private int availableCopies;

    private LocalDate nextAvailableCopy;
    private List<Reservation> reservations;

    private boolean reservable;
}
