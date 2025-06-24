package com.oc.projet7api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oc.projet7api.mapper.UserMapper;
import com.oc.projet7api.model.dto.UserLoginDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.User;
import com.oc.projet7api.repository.LoanRepository;
import com.oc.projet7api.repository.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserResponseDTO authenticate(UserLoginDTO userLoginDTO) {
		User user = userRepository.findByEmail(userLoginDTO.getEmail());

		if (user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
			return null;
		}
		
		List<Loan> loans = loanRepository.findAllByUserId(user.getId());
		
		return UserMapper.toResponseDTO(user, loans);
	}
}
