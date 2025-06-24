package com.oc.projet7app.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String email;
	
	private String firstName;
	private String lastName;
	
	private List<Loan> loans;
}
