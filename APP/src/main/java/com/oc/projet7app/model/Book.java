package com.oc.projet7app.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Book {
	private Long id;
	
	private String title;
	private String author;
	
	private LocalDate releaseDate;
	
	private int totalCopies;
	private int availableCopies;

	private List<Reservation> reservations;

	public boolean reservable;
}
