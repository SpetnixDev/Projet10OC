package com.oc.projet7api.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanUserResponseDTO {
	private Long id;
	
	private String bookTitle;
	private String bookAuthor;
	
	private LocalDate loanDate;
	private LocalDate returnDate;
	
	private boolean returned;
	private boolean extended;
}
