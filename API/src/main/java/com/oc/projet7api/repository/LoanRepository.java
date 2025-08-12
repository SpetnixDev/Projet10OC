package com.oc.projet7api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oc.projet7api.model.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	Optional<Loan> findById(Long id);
	
	List<Loan> findAllByUserId(Long userId);
	
	@Query(value = "SELECT * FROM loans WHERE return_date < :date AND returned = false", nativeQuery = true)
    List<Loan> findOverdueReservations(LocalDate date);

	@Query(value = "SELECT return_date FROM loans WHERE book_id = :bookId AND returned = false ORDER BY return_date ASC LIMIT 1", nativeQuery = true)
    LocalDate findNextAvailableCopy(Long bookId);
}
