package com.oc.projet7api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import com.oc.projet7api.model.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oc.projet7api.mapper.UserMapper;
import com.oc.projet7api.model.dto.UserLoginDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.UserRepository;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ShouldReturnUserResponseDTO_WhenCredentialsAreValid() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test@example.com", "password");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);

        List<Loan> loans = Collections.singletonList(loan);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            when(userRepository.findByEmail(userLoginDTO.getEmail())).thenReturn(user);
            when(passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())).thenReturn(true);
            when(loanRepository.findAllByUserId(user.getId())).thenReturn(loans);

            mockedUserMapper.when(() -> UserMapper.toResponseDTO(user, loans)).thenReturn(new UserResponseDTO());

            UserResponseDTO result = authenticationService.authenticate(userLoginDTO);

            assertNotNull(result);
            verify(userRepository).findByEmail(userLoginDTO.getEmail());
            verify(passwordEncoder).matches(userLoginDTO.getPassword(), user.getPassword());
            verify(loanRepository).findAllByUserId(user.getId());
        }
    }

    @Test
    void authenticate_ShouldReturnNull_WhenUserNotFound() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test@example.com", "password");

        when(userRepository.findByEmail(userLoginDTO.getEmail())).thenReturn(null);

        UserResponseDTO result = authenticationService.authenticate(userLoginDTO);

        assertNull(result);
        verify(userRepository).findByEmail(userLoginDTO.getEmail());
        verifyNoInteractions(passwordEncoder, loanRepository);
    }

    @Test
    void authenticate_ShouldReturnNull_WhenPasswordDoesNotMatch() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test@example.com", "password");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(userLoginDTO.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())).thenReturn(false);

        UserResponseDTO result = authenticationService.authenticate(userLoginDTO);

        assertNull(result);
        verify(userRepository).findByEmail(userLoginDTO.getEmail());
        verify(passwordEncoder).matches(userLoginDTO.getPassword(), user.getPassword());
        verifyNoInteractions(loanRepository);
    }
}