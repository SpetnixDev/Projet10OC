package com.oc.projet7api.service;

import java.util.List;

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
	
	public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}
	
	public Book save(BookDTO bookDto) {
		Book book = BookMapper.toBook(bookDto);
		
		return bookRepository.save(book);
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public List<Book> searchBooksByKeywords(List<String> keywords) {
		return bookRepository.findAll(BookSpecifications.hasKeywords(keywords));
	}
	
	public List<Book> findLastBooksAdded() {
		return bookRepository.findTop5ByOrderByIdDesc();
	}
	
	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
		
		bookRepository.delete(book);
	}
}
