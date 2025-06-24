package com.oc.projet7app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.oc.projet7app.model.Loan;
import com.oc.projet7app.model.dto.LoanDTO;

import reactor.core.publisher.Mono;

@Service
public class LoanService {
	@Autowired
	private WebClient webClient;
	
	public Mono<Loan> create(Long bookId, Long userId) {
		LoanDTO loanDTO = new LoanDTO();

		loanDTO.setBookId(bookId);
		loanDTO.setUserId(userId);
		
		return webClient.post().uri("/loans/create").bodyValue(loanDTO).retrieve().bodyToMono(Loan.class);
	}

	public Mono<Loan> extend(Long id) {
		return webClient.put().uri("/loans/" + id + "/extend").retrieve().bodyToMono(Loan.class);
	}
}
