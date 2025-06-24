package com.oc.projet7api.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long id;
	
	private String email;
	
	private String firstName;
	private String lastName;
	
	private List<LoanUserResponseDTO> loans;
}
