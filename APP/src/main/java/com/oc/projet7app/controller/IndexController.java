package com.oc.projet7app.controller;

import java.util.List;

import com.oc.projet7app.model.User;
import com.oc.projet7app.service.ReservationService;
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

    @Autowired
    private ReservationService reservationService;
	
	@GetMapping
	public String home(HttpServletRequest request, Model model) {
		List<Book> books = bookService.getLastBooksAdded().block();
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && books != null) {
            Long userId = user.getId();

            books.forEach(book -> {
                boolean userHasReservation = Boolean.TRUE.equals(reservationService.userHasActiveReservation(userId, book.getId()).block());
                book.setReservableByUser(book.isReservable() && !userHasReservation);
            });
        }
		
		model.addAttribute("books", books);
		
		return "index";
	}
}
