package com.oc.projet7api.model.dto;

import java.time.Instant;

public class ReservationResponseDTO {
    private Long id;
    private String bookTitle;
    private String bookAuthor;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private int position;
    private Instant notified;
}
