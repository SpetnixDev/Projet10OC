package com.oc.projet7batch.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Reservation {
    private Long id;
    private Long bookId;
    private Long userId;
    private int position;
    private Instant notified;
}
