package com.oc.projet7api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oc.projet7api.mapper.UserMapper;
import com.oc.projet7api.model.dto.UserDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserResponseDTO findById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		List<Loan> loans = loanRepository.findAllByUserId(id);
		List<ReservationProjection> reservations = reservationRepository.findAllByUserId(id);
		
		return UserMapper.toResponseDTO(user, loans, reservations);
	}
	
	public UserResponseDTO save(UserDTO userDto) {
		User user = UserMapper.toUser(userDto);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return UserMapper.toResponseDTO(userRepository.save(user), new ArrayList<>(), new ArrayList<>());
	}

	public List<UserResponseDTO> findAll() {
		List<User> users = userRepository.findAll();
		
		return users.stream().map(user -> UserMapper.toResponseDTO(user, new ArrayList<>(), new ArrayList<>())).collect(Collectors.toList());
	}

	public void delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		userRepository.delete(user);
	}
}
