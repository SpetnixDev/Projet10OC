package com.oc.projet7app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.projet7app.model.Loan;
import com.oc.projet7app.model.User;
import com.oc.projet7app.service.LoanService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("loan")
public class LoanController {
	@Autowired
	private LoanService loanService;
	
	@PostMapping("/create/{id}")
	public String create(@PathVariable("id") Long id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Loan loan = loanService.create(id, user.getId()).block();
		
		user.getLoans().add(loan);
	    
	    request.getSession().setAttribute("user", user);
		
		return "redirect:/profile";
	}
	
	@PutMapping("/{id}/extend")
	public void extend(@PathVariable("id") Long id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user"); 
		Loan loan = loanService.extend(id).block();
		
		user.getLoans().replaceAll(l -> l.getId().equals(id) ? loan : l);
	    
	    request.getSession().setAttribute("user", user);
	}
}
