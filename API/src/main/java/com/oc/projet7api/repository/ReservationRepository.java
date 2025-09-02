package com.oc.projet7api.repository;

import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    int countByBookId(Long bookId);

    List<Reservation> findAllByBookIdOrderByPositionAsc(Long bookId);

    Optional<Reservation> findFirstByBookIdOrderByPositionAsc(Long bookId);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

    Optional<Reservation> findByBookIdAndUserId(Long bookId, Long userId);

    @Query("""
        SELECT r.id AS id,
               b.title AS bookTitle,
               b.author AS bookAuthor,
               r.position AS position,
               (SELECT MIN(l.returnDate)
                FROM Loan l
                WHERE l.book.id = b.id AND l.returned = false) AS nextAvailableCopy
        FROM Reservation r
        JOIN r.book b
        WHERE r.id = :id
    """)
    ReservationProjection findProjectionById(Long id);

    @Query("""
        SELECT r.id AS id,
               b.title AS bookTitle,
               b.author AS bookAuthor,
               r.position AS position,
               (SELECT MIN(l.returnDate)
                FROM Loan l
                WHERE l.book.id = b.id AND l.returned = false) AS nextAvailableCopy
        FROM Reservation r
        JOIN r.book b
        WHERE r.user.id = :userId
    """)
    List<ReservationProjection> findAllByUserId(Long userId);

    // This method is used to find all first reservations for each book with a query
    @Query("""
        SELECT r
        FROM Reservation r
        WHERE r.position = 1
    """)
    List<Reservation> findAllFirstReservations();
}
