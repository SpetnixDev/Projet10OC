package com.oc.projet7api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.oc.projet7api.model.dto.LoanUserResponseDTO;
import com.oc.projet7api.model.dto.ReservationProjection;
import com.oc.projet7api.model.dto.UserDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.model.entity.Loan;
import com.oc.projet7api.model.entity.Reservation;
import com.oc.projet7api.model.entity.User;

public class UserMapper {
	public static UserResponseDTO toResponseDTO(User user, List<Loan> loans, List<ReservationProjection> reservations) {
		UserResponseDTO dto = new UserResponseDTO();
		
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		
		List<LoanUserResponseDTO> responseLoans = new ArrayList<>();
		
		for (Loan loan : loans) {
			responseLoans.add(LoanMapper.toUserResponseDTO(loan));
		}
		
		dto.setLoans(responseLoans);
		dto.setReservations(reservations);
		
		return dto;
	}
	
	public static User toUser(UserDTO userDto) {
		User user = new User();
		
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		
		return user;
	}
}
