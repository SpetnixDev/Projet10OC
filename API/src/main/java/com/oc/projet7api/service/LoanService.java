package com.oc.projet7api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.repository.ReservationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.projet7api.mapper.LoanMapper;
import com.oc.projet7api.model.dto.LoanDTO;
import com.oc.projet7api.model.dto.LoanResponseDTO;
import com.oc.projet7api.model.dto.LoanUserResponseDTO;
import com.oc.projet7api.model.entity.Book;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.BookRepository;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private MailService mailService;
	
	public LoanUserResponseDTO findById(long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
		
		return LoanMapper.toUserResponseDTO(loan);
	}

	@Transactional
	public LoanUserResponseDTO create(LoanDTO loanDto) {
		Loan loan = new Loan();
		
		User user = userRepository.findById(loanDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		Book book = bookRepository.findById(loanDto.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
		
		loan.setUser(user);
		loan.setBook(book);
		
		loan.setLoanDate(LocalDate.now());
		loan.setReturnDate(LocalDate.now().plusWeeks(4));
		
		book.setAvailableCopies(book.getAvailableCopies() - 1);
	    bookRepository.save(book);

		Optional<Reservation> reservation = reservationRepository.findByBookIdAndUserId(book.getId(), user.getId());
		reservation.ifPresent(value -> reservationService.cancelReservation(value.getId()));
		
		return LoanMapper.toUserResponseDTO(loanRepository.save(loan));
	}

	public LoanUserResponseDTO extend(Long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));

		if (loan.isExtended()) {
			throw new RuntimeException("Un prêt ne peut pas être prolongé plus d'une fois");
		}

		if (loan.getReturnDate().isBefore(LocalDate.now())) {
			throw new RuntimeException("Un prêt ne peut pas être prolongé après la date de retour");
		}
		
		loan.extend();
		
		return LoanMapper.toUserResponseDTO(loanRepository.save(loan));
	}

	public List<LoanResponseDTO> getOverdueLoans() {
		LocalDate today = LocalDate.now();
		
		return loanRepository.findOverdueReservations(today).stream().map(LoanMapper::toResponseDTO).collect(Collectors.toList());
	}
	
	public void delete(long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
		
		if (!loan.isReturned()) {
			Book book = loan.getBook();
			
			book.setAvailableCopies(book.getAvailableCopies() + 1);
		    bookRepository.save(book);
		}
	    
		loanRepository.deleteById(id);
	}

	@Transactional
	public LoanUserResponseDTO completeLoan(Long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
		Book book = loan.getBook();

		Optional<Reservation> reservation = reservationService.findFirstByBookId(book.getId());

        reservation.ifPresent(value -> {
            try {
                mailService.sendAvailableBookEmail(value);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
		
		if (!loan.isReturned()) {
			loan.setReturned(true);
			book.setAvailableCopies(book.getAvailableCopies() + 1);
			
			bookRepository.save(book);
			loanRepository.save(loan);
		}
		
		return LoanMapper.toUserResponseDTO(loan);
	}

	public LocalDate getNextAvailableCopy(Long bookId) {
		return loanRepository.findNextAvailableCopy(bookId);
	}
}
