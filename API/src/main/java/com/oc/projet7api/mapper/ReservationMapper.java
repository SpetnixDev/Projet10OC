package com.oc.projet7api.mapper;

import com.oc.projet7api.model.dto.ReservationResponseBatchDTO;
import com.oc.projet7api.model.entity.Reservation;

public class ReservationMapper {
    public static ReservationResponseBatchDTO toResponseBatchDTO(Reservation reservation) {
        ReservationResponseBatchDTO dto = new ReservationResponseBatchDTO();

        dto.setId(reservation.getId());
        dto.setBookId(reservation.getBook().getId());
        dto.setUserId(reservation.getUser().getId());
        dto.setPosition(reservation.getPosition());
        dto.setNotified(reservation.getNotified());

        return dto;
    }
}
