package com.oc.projet7app.controller;

import java.util.Arrays;
import java.util.List;

import com.oc.projet7app.model.User;
import com.oc.projet7app.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oc.projet7app.model.Book;
import com.oc.projet7app.service.BookService;
import reactor.netty.http.server.HttpServerRequest;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private BookService bookService;

    @Autowired
    private ReservationService reservationService;
	
	@GetMapping
    public String search(@RequestParam(value = "keywords", required = false) String keywords, HttpServletRequest request, Model model) {
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

        User user = (User) request.getSession().getAttribute("user");

        if (user != null && books != null) {
            Long userId = user.getId();
            
            books.forEach(book -> {
                boolean userHasReservation = Boolean.TRUE.equals(reservationService.userHasActiveReservation(userId, book.getId()).block());
                book.setReservableByUser(book.isReservable() && !userHasReservation);
            });
        }

        model.addAttribute("books", books);
        
        return "search";
    }
}
