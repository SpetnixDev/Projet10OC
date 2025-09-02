package com.oc.projet7api.service;

import java.time.LocalDate;
import java.util.List;

import com.oc.projet7api.model.dto.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.projet7api.mapper.BookMapper;
import com.oc.projet7api.model.dto.BookDTO;
import com.oc.projet7api.model.entity.Book;
import com.oc.projet7api.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LoanService loanService;
	
	public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}
	
	public Book save(BookDTO bookDto) {
		Book book = BookMapper.toBook(bookDto);
		
		return bookRepository.save(book);
	}

	public List<BookResponseDTO> findAll() {
		List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> {
                    LocalDate nextAvailableCopy = null;
                    if (book.isReservable()) nextAvailableCopy = loanService.getNextAvailableCopy(book.getId());
					return BookMapper.toBookResponseDTO(book, nextAvailableCopy);
                })
                .toList();
	}
	
	public List<BookResponseDTO> searchBooksByKeywords(List<String> keywords) {
		List<Book> books = bookRepository.findAll(BookSpecifications.hasKeywords(keywords));

		return books.stream()
				.map(book -> {
					LocalDate nextAvailableCopy = null;
					if (book.isReservable()) nextAvailableCopy = loanService.getNextAvailableCopy(book.getId());
					return BookMapper.toBookResponseDTO(book, nextAvailableCopy);
				})
				.toList();
	}
	
	public List<BookResponseDTO> findLastBooksAdded() {
		List<Book> books = bookRepository.findTop5ByOrderByIdDesc();

		return books.stream()
				.map(book -> {
					LocalDate nextAvailableCopy = null;
					if (book.isReservable()) nextAvailableCopy = loanService.getNextAvailableCopy(book.getId());
					return BookMapper.toBookResponseDTO(book, nextAvailableCopy);
				})
				.toList();
	}
	
	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
		
		bookRepository.delete(book);
	}
}
