package com.oc.projet7app.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Loan {
	private Long id;
	
	private String bookTitle;
	private String bookAuthor;
	
	private LocalDate loanDate;
	private LocalDate returnDate;
	
	private boolean returned;
	private boolean extended;

}
