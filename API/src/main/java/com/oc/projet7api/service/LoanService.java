package com.oc.projet7api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.repository.ReservationRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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
	private MailService mailService;
	
	public LoanUserResponseDTO findById(long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
		
		return LoanMapper.toUserResponseDTO(loan);
	}

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
		
		return LoanMapper.toUserResponseDTO(loanRepository.save(loan));
	}

	public LoanUserResponseDTO extend(Long id) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
		
		loan.extend();
		
		return LoanMapper.toUserResponseDTO(loanRepository.save(loan));
	}

	public List<LoanResponseDTO> getOverdueLoans() {
		LocalDate today = LocalDate.now();
		
		return loanRepository.findOverdueReservations(today).stream().map(loan -> LoanMapper.toResponseDTO(loan)).collect(Collectors.toList());
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
}
