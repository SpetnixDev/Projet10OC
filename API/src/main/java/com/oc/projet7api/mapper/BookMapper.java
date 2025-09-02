package com.oc.projet7api.mapper;

import com.oc.projet7api.model.dto.BookDTO;
import com.oc.projet7api.model.dto.BookResponseDTO;
import com.oc.projet7api.model.entity.Book;

import java.time.LocalDate;

public class BookMapper {
	public static Book toBook(BookDTO bookDto) {
		Book book = new Book();
		
		book.setAuthor(bookDto.getAuthor());
		book.setTitle(bookDto.getTitle());
		book.setTotalCopies(bookDto.getTotalCopies());
		book.setAvailableCopies(bookDto.getTotalCopies());
		book.setReleaseDate(bookDto.getReleaseDate());
		
		return book;
	}

	public static BookResponseDTO toBookResponseDTO(Book book, LocalDate nextAvailableCopy) {
		BookResponseDTO bookResponse = new BookResponseDTO();

		bookResponse.setId(book.getId());
		bookResponse.setTitle(book.getTitle());
		bookResponse.setAuthor(book.getAuthor());
		bookResponse.setReleaseDate(book.getReleaseDate());
		bookResponse.setTotalCopies(book.getTotalCopies());
		bookResponse.setAvailableCopies(book.getAvailableCopies());
		bookResponse.setNextAvailableCopy(nextAvailableCopy);
		bookResponse.setReservations(book.getReservations());
		bookResponse.setReservable(book.isReservable());

		return bookResponse;
	}
}
