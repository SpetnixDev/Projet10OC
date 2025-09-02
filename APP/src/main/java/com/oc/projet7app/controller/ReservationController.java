package com.oc.projet7app.controller;

import com.oc.projet7app.model.Reservation;
import com.oc.projet7app.model.User;
import com.oc.projet7app.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create/{id}")
    public void create(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Reservation reservation = reservationService.createReservation(user.getId(), id).block();

        user.getReservations().add(reservation);

        request.getSession().setAttribute("user", user);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        reservationService.cancelReservation(id).block();

        user.getReservations().removeIf(r -> r.getId().equals(id));

        request.getSession().setAttribute("user", user);
    }
}
