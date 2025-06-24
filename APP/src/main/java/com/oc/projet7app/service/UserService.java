package com.oc.projet7app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.oc.projet7app.exception.AuthenticationException;
import com.oc.projet7app.model.User;
import com.oc.projet7app.model.dto.UserDTO;
import com.oc.projet7app.model.dto.UserLoginDTO;

import reactor.core.publisher.Mono;

@Service
public class UserService {
	@Autowired
	private WebClient webClient;
	
	public Mono<List<User>> getAllUsers() {
		return webClient.get().uri("/users").retrieve().bodyToMono(new ParameterizedTypeReference<List<User>>(){});
	}
	
	public Mono<User> registerUser(UserDTO userDTO) {
		return webClient.post().uri("/users/create").bodyValue(userDTO).retrieve().bodyToMono(User.class);
	}

	public Mono<User> authenticateUser(UserLoginDTO userLoginDTO) {
		return webClient.post()
				.uri("/users/login")
				.bodyValue(userLoginDTO)
				.retrieve()
				.onStatus(HttpStatusCode::isError, 
						clientResponse -> clientResponse.bodyToMono(String.class)
						.flatMap(errorMessage -> Mono.error(new AuthenticationException(errorMessage))))
				.bodyToMono(User.class);
	}
}
