package com.oc.projet7batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.oc.projet7batch.model.Loan;

@Service
public class LoanService {
	@Autowired
	private WebClient webClient;
	
	public List<Loan> getOverdueLoans() {
		return webClient.get().uri("/loans/overdue").retrieve().bodyToFlux(Loan.class).collectList().block();
	}
}
