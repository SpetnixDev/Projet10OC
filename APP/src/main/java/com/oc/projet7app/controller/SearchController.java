package com.oc.projet7app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oc.projet7app.model.Book;
import com.oc.projet7app.service.BookService;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private BookService bookService;
	
	@GetMapping
    public String search(@RequestParam(value = "keywords", required = false) String keywords, Model model) {
        List<Book> books;

        if (keywords == null || keywords.isEmpty()) {
            books = bookService.getAllBooks().block();
        } else {
            books = bookService.getBooksByKeywords(
                Arrays.stream(keywords.split(" "))
                      .map(String::trim)
                      .filter(s -> !s.isEmpty())
                      .toList()
            ).block();
        }

        model.addAttribute("books", books);
        
        return "search";
    }
}
