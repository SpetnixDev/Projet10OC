package com.oc.projet7api.controller;

import java.util.List;

import com.oc.projet7api.model.dto.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.projet7api.model.dto.BookDTO;
import com.oc.projet7api.model.entity.Book;
import com.oc.projet7api.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping("/{id}")
	public Book findById(@PathVariable long id) {
		return bookService.findById(id);
	}
	
	@GetMapping
	public List<BookResponseDTO> findAll() {
		return bookService.findAll();
	}
	
	@PostMapping("/add")
	public Book add(@RequestBody BookDTO bookDto) {
		return bookService.save(bookDto);
	}
	
	@GetMapping("/search")
    public List<BookResponseDTO> searchBooks(@RequestParam List<String> keywords) {
        return bookService.searchBooksByKeywords(keywords);
    }
	
	@GetMapping("/last")
	public List<BookResponseDTO> findLastBooksAdded() {
		return bookService.findLastBooksAdded();
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestParam Long id) {
		bookService.delete(id);
	}
}
