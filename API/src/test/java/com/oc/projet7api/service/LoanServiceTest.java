package com.oc.projet7api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnLoanUserResponseDTO_WhenLoanExists() {
        Loan loan = new Loan();
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        try (var mockedLoanMapper = mockStatic(LoanMapper.class)) {
            mockedLoanMapper.when(() -> LoanMapper.toUserResponseDTO(loan)).thenReturn(new LoanUserResponseDTO());

            LoanUserResponseDTO result = loanService.findById(1L);

            assertNotNull(result);
            verify(loanRepository).findById(1L);
        }
    }

    @Test
    void create_ShouldReturnLoanUserResponseDTO_WhenLoanIsCreated() {
        LoanDTO loanDto = new LoanDTO();
        loanDto.setUserId(1L);
        loanDto.setBookId(1L);

        User user = new User();
        Book book = new Book();
        book.setAvailableCopies(5);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        try (var mockedLoanMapper = mockStatic(LoanMapper.class)) {
            mockedLoanMapper.when(() -> LoanMapper.toUserResponseDTO(loan)).thenReturn(new LoanUserResponseDTO());

            LoanUserResponseDTO result = loanService.create(loanDto);

            assertNotNull(result);
            assertEquals(4, book.getAvailableCopies());
            verify(userRepository).findById(1L);
            verify(bookRepository).findById(1L);
            verify(bookRepository).save(book);
            verify(loanRepository).save(any(Loan.class));
        }
    }

    @Test
    void extend_ShouldThrowException_WhenLoanIsAlreadyExtended() {
        Loan loan = new Loan();
        loan.setExtended(true);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Exception exception = assertThrows(RuntimeException.class, () -> loanService.extend(1L));
        assertEquals("Un prêt ne peut pas être prolongé plus d'une fois", exception.getMessage());
        verify(loanRepository).findById(1L);
    }

    @Test
    void getOverdueLoans_ShouldReturnListOfLoanResponseDTO() {
        Loan loan = new Loan();
        List<Loan> overdueLoans = Collections.singletonList(loan);

        when(loanRepository.findOverdueReservations(LocalDate.now())).thenReturn(overdueLoans);
        try (var mockedLoanMapper = mockStatic(LoanMapper.class)) {
            mockedLoanMapper.when(() -> LoanMapper.toResponseDTO(loan)).thenReturn(new LoanResponseDTO());

            List<LoanResponseDTO> result = loanService.getOverdueLoans();

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(loanRepository).findOverdueReservations(LocalDate.now());
        }
    }

    @Test
    void delete_ShouldIncreaseBookCopies_WhenLoanIsNotReturned() {
        Loan loan = new Loan();
        Book book = new Book();
        book.setAvailableCopies(1);
        loan.setBook(book);
        loan.setReturned(false);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.delete(1L);

        assertEquals(2, book.getAvailableCopies());
        verify(bookRepository).save(book);
        verify(loanRepository).deleteById(1L);
    }

    @Test
    void completeLoan_ShouldReturnLoanUserResponseDTO_WhenLoanIsCompleted() {
        Loan loan = new Loan();
        Book book = new Book();
        book.setAvailableCopies(1);
        loan.setBook(book);
        loan.setReturned(false);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(loan)).thenReturn(loan);

        try (var mockedLoanMapper = mockStatic(LoanMapper.class)) {
            mockedLoanMapper.when(() -> LoanMapper.toUserResponseDTO(loan)).thenReturn(new LoanUserResponseDTO());

            LoanUserResponseDTO result = loanService.completeLoan(1L);

            assertNotNull(result);
            assertTrue(loan.isReturned());
            assertEquals(2, book.getAvailableCopies());
            verify(bookRepository).save(book);
            verify(loanRepository).save(loan);
        }
    }
}