package com.oc.projet7api.model.dto;

import lombok.Data;

import java.time.LocalDate;

public interface ReservationProjection {
    Long getId();

    String getBookTitle();
    String getBookAuthor();

    LocalDate getNextAvailableCopy();
    int getPosition();
}
