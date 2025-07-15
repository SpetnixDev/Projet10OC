package com.oc.projet7api.service;

import com.oc.projet7api.model.dto.ReservationDTO;
import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.entity.Book;
import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.BookRepository;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.ReservationRepository;
import com.oc.projet7api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Transactional
    public ReservationProjection createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        Long bookId = reservationDTO.getBookId();
        Long userId = reservationDTO.getUserId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Le livre n'existe pas"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));


        if (book.getAvailableCopies() > 0) {
            throw new RuntimeException("Des exemplaires de ce livre sont disponibles, pas besoin de réservation");
        }

        int maxReservations = book.getTotalCopies() * 2;
        int currentReservations = reservationRepository.countByBookId(bookId);

        if (currentReservations >= maxReservations) {
            throw new RuntimeException("Le nombre maximum de réservations pour ce livre a été atteint");
        }

        boolean hasActiveLoan = loanRepository.findAllByUserId(userId).stream()
                .anyMatch(loan ->  loan.getBook().getId().equals(bookId) && !loan.isReturned());

        if (hasActiveLoan) {
            throw new RuntimeException("Vous avez déjà un prêt actif pour ce livre");
        }

        boolean alreadyReserved = reservationRepository.existsByBookIdAndUserId(bookId, userId);

        if (alreadyReserved) {
            throw new RuntimeException("Vous avez déjà réservé ce livre");
        }

        int position = currentReservations + 1;
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setPosition(position);

        Reservation newReservation = reservationRepository.save(reservation);
        return reservationRepository.findProjectionById(newReservation.getId());
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        Long bookId = reservation.getBook().getId();

        reservationRepository.delete(reservation);

        reservationRepository.findAllByBookIdOrderByPositionAsc(bookId)
                .forEach(res -> {
                    if (res.getPosition() > reservation.getPosition()) {
                        res.setPosition(res.getPosition() - 1);
                        reservationRepository.save(res);
                    }
                });
    }
}
