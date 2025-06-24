package com.oc.projet7api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oc.projet7api.model.dto.LoanDTO;
import com.oc.projet7api.model.dto.LoanResponseDTO;
import com.oc.projet7api.model.dto.LoanUserResponseDTO;
import com.oc.projet7api.service.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanController {
	@Autowired
	private LoanService loanService;
	
	@GetMapping("/{id}")
	public LoanUserResponseDTO findById(@PathVariable long id) {
		return loanService.findById(id);
	}
	
	@PostMapping("/create")
	public LoanUserResponseDTO create(@RequestBody LoanDTO loanDto) {
		return loanService.create(loanDto);
	}
	
	@PutMapping("/{id}/extend")
	public LoanUserResponseDTO extend(@PathVariable Long id) {
		return loanService.extend(id);
	}
	
	@GetMapping("/overdue")
	public List<LoanResponseDTO> getOverdueLoans() {
		return loanService.getOverdueLoans();
	}
}
