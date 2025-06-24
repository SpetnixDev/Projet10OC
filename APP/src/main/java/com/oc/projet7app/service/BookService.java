package com.oc.projet7app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.oc.projet7app.model.Book;

import reactor.core.publisher.Mono;

@Service
public class BookService {
	@Autowired
	private WebClient webClient;
	
	public Mono<List<Book>> getAllBooks() {
		return webClient.get().uri("/books").retrieve().bodyToMono(new ParameterizedTypeReference<List<Book>>(){});
	}
	
	public Mono<List<Book>> getBooksByKeywords(List<String> keywords) {
		return webClient.get().uri(uriBuilder -> uriBuilder.path("/books/search").queryParam("keywords", keywords).build()).retrieve().bodyToMono(new ParameterizedTypeReference<List<Book>>(){});
	}
	
	public Mono<List<Book>> getLastBooksAdded() {
		return webClient.get().uri("/books/last").retrieve().bodyToMono(new ParameterizedTypeReference<List<Book>>(){});
	}
}
