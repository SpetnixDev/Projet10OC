package com.oc.projet7api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oc.projet7api.mapper.UserMapper;
import com.oc.projet7api.model.dto.UserDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnUserResponseDTO_WhenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");

        List<Loan> loans = Collections.singletonList(new Loan());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.findAllByUserId(userId)).thenReturn(loans);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.toResponseDTO(user, loans)).thenReturn(new UserResponseDTO());

            UserResponseDTO result = userService.findById(userId);

            assertNotNull(result);
            verify(userRepository).findById(userId);
            verify(loanRepository).findAllByUserId(userId);
        }
    }

    @Test
    void findById_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findById(userId));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(userId);
        verifyNoInteractions(loanRepository);
    }

    @Test
    void save_ShouldReturnUserResponseDTO_WhenUserIsSaved() {
        UserDTO userDto = new UserDTO();
        userDto.setPassword("password");

        User user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.toUser(userDto)).thenReturn(user);
            mockedUserMapper.when(() -> UserMapper.toResponseDTO(user, new ArrayList<>())).thenReturn(new UserResponseDTO());

            UserResponseDTO result = userService.save(userDto);

            assertNotNull(result);
            verify(passwordEncoder).encode(userDto.getPassword());
            verify(userRepository).save(user);
        }
    }

    @Test
    void findAll_ShouldReturnListOfUserResponseDTO() {
        User user = new User();
        List<User> users = Collections.singletonList(user);

        when(userRepository.findAll()).thenReturn(users);

        try (var mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.toResponseDTO(user, new ArrayList<>())).thenReturn(new UserResponseDTO());

            List<UserResponseDTO> result = userService.findAll();

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(userRepository).findAll();
        }
    }

    @Test
    void delete_ShouldDeleteUser_WhenUserExists() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void delete_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.delete(userId));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }
}
