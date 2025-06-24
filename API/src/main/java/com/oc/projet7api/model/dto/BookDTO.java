package com.oc.projet7api.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookDTO {
	private String title;
	private String author;
	
	private LocalDate releaseDate;
	private int totalCopies;
}
