package com.oc.projet7app.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Reservation {
    private Long id;

    private String bookTitle;
    private String bookAuthor;

    private LocalDate nextAvailableCopy;
    private int position;
}
