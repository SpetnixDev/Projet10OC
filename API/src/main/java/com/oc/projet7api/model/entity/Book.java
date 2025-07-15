package com.oc.projet7api.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String author;
	
	private LocalDate releaseDate;
	
	private int totalCopies;
	private int availableCopies;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("position ASC")
	private List<Reservation> reservations;

	@Transient
	private boolean reservable;

	@PostLoad
	public void calculateReservable() {
		this.reservable = reservations.size() < totalCopies * 2 && availableCopies == 0;
	}
}
