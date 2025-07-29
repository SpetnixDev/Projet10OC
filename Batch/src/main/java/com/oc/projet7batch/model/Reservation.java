package com.oc.projet7batch.model;

import lombok.Data;

@Data
public class Reservation {
    private Long id;
    private String bookTitle;
    private String bookAuthor;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private int position;
}
