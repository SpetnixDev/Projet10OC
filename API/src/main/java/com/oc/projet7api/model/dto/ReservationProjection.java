package com.oc.projet7api.model.dto;

import lombok.Data;

import java.time.LocalDate;

public interface ReservationProjection {
    public Long getId();

    public String getBookTitle();
    public String getBookAuthor();

    public LocalDate getNextAvailableCopy();
    public int getPosition();
}
