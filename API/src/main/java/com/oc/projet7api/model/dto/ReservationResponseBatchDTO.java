package com.oc.projet7api.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ReservationResponseBatchDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private int position;
    private Instant notified;
}
