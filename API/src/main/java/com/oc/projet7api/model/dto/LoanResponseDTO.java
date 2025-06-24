package com.oc.projet7api.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanResponseDTO {
	private Long id;
	
	private String bookTitle;
	private String bookAuthor;
	
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	
	private LocalDate loanDate;
	private LocalDate returnDate;
	
	private boolean returned;
	private boolean extended;
}
