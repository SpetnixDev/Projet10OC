package com.oc.projet7api.service;

import com.oc.projet7api.model.dto.ReservationDTO;
import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.entity.Book;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.BookRepository;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.ReservationRepository;
import com.oc.projet7api.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReservation_shouldThrowIfBookNotFound() {
        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldThrowIfUserNotFound() {
        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldThrowIfBookAvailable() {
        Book book = new Book();
        book.setAvailableCopies(1);
        book.setTotalCopies(2);

        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldThrowIfMaxReservationsReached() {
        Book book = new Book();
        book.setAvailableCopies(0);
        book.setTotalCopies(1);

        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(reservationRepository.countByBookId(1L)).thenReturn(2);

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldThrowIfUserHasActiveLoan() {
        Book book = new Book();
        book.setAvailableCopies(0);
        book.setTotalCopies(2);

        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(reservationRepository.countByBookId(1L)).thenReturn(0);

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(new User());
        reservation.setPosition(1);

        Loan loan = mock(Loan.class);

        when(loan.getBook()).thenReturn(book);
        when(loan.isReturned()).thenReturn(false);
        when(loanRepository.findAllByUserId(2L)).thenReturn(List.of(loan));

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldThrowIfAlreadyReserved() {
        Book book = new Book();
        book.setAvailableCopies(0);
        book.setTotalCopies(2);

        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(reservationRepository.countByBookId(1L)).thenReturn(0);
        when(loanRepository.findAllByUserId(2L)).thenReturn(Collections.emptyList());
        when(reservationRepository.existsByBookIdAndUserId(1L, 2L)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(dto));
    }

    @Test
    void createReservation_shouldCreateReservation() {
        Book book = new Book();
        book.setAvailableCopies(0);
        book.setTotalCopies(2);

        User user = new User();

        ReservationDTO dto = new ReservationDTO();
        dto.setBookId(1L);
        dto.setUserId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(reservationRepository.countByBookId(1L)).thenReturn(0);
        when(loanRepository.findAllByUserId(2L)).thenReturn(Collections.emptyList());
        when(reservationRepository.existsByBookIdAndUserId(1L, 2L)).thenReturn(false);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(10L);
        when(reservationRepository.save(any())).thenReturn(savedReservation);

        ReservationProjection projection = mock(ReservationProjection.class);
        when(reservationRepository.findProjectionById(10L)).thenReturn(projection);

        assertEquals(projection, reservationService.createReservation(dto));
    }

    @Test
    void cancelReservation_shouldDeleteAndUpdatePositions() {
        Book book = new Book();
        book.setId(2L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setBook(book);
        reservation.setPosition(1);

        Reservation res2 = new Reservation();
        res2.setId(3L);
        res2.setBook(book);
        res2.setPosition(2);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.findAllByBookIdOrderByPositionAsc(2L)).thenReturn(List.of(res2));

        reservationService.cancelReservation(1L);

        verify(reservationRepository).delete(reservation);
        verify(reservationRepository).save(res2);

        assertEquals(1, res2.getPosition());
    }

    @Test
    void findFirstByBookId_shouldReturnOptional() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findFirstByBookIdOrderByPositionAsc(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.findFirstByBookId(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void updateReservationNotification_shouldUpdateNotified() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        reservationService.updateReservationNotification(1L);

        verify(reservationRepository).save(reservation);
        assertNotNull(reservation.getNotified());
    }

    @Test
    void rotateReservations_shouldDoNothingIfEmpty() throws MessagingException {
        when(reservationRepository.findAllByBookIdOrderByPositionAsc(1L)).thenReturn(Collections.emptyList());
        reservationService.rotateReservations(1L);

        verify(reservationRepository, never()).delete(any());
    }

    @Test
    void rotateReservations_shouldRotateAndNotify() throws MessagingException {
        Book book = new Book();
        book.setId(1L);

        Reservation first = new Reservation();
        first.setId(1L);
        first.setBook(book);
        first.setPosition(1);

        Reservation second = new Reservation();
        second.setId(2L);
        second.setBook(book);
        second.setPosition(2);

        when(reservationRepository.findAllByBookIdOrderByPositionAsc(1L)).thenReturn(List.of(first, second));
        when(reservationRepository.save(any())).thenReturn(second);

        reservationService.rotateReservations(1L);

        verify(reservationRepository).delete(first);
        verify(mailService).sendAvailableBookEmail(second);
        verify(reservationRepository).save(second);
    }

    @Test
    void userHasReservation_shouldReturnTrueOrFalse() {
        when(reservationRepository.existsByBookIdAndUserId(1L, 2L)).thenReturn(true);
        assertTrue(reservationService.userHasReservation(2L, 1L));

        when(reservationRepository.existsByBookIdAndUserId(1L, 2L)).thenReturn(false);
        assertFalse(reservationService.userHasReservation(2L, 1L));
    }
}
