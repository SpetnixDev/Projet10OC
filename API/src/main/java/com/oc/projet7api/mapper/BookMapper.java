package com.oc.projet7api.mapper;

import com.oc.projet7api.model.dto.BookDTO;
import com.oc.projet7api.model.entity.Book;

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
}
