package com.oc.projet7app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.projet7app.model.Book;
import com.oc.projet7app.service.BookService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public String home(HttpServletRequest request, Model model) {
		List<Book> books = bookService.getLastBooksAdded().block();
		
		model.addAttribute("books", books);
		
		return "index";
	}
}
