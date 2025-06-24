package com.oc.projet7api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oc.projet7api.model.dto.UserDTO;
import com.oc.projet7api.model.dto.UserLoginDTO;
import com.oc.projet7api.model.dto.UserResponseDTO;
import com.oc.projet7api.service.AuthenticationService;
import com.oc.projet7api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping("/{id}")
	public UserResponseDTO findById(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@GetMapping
	public List<UserResponseDTO> findAll() {
		return userService.findAll();
	}
	
	@PostMapping("/create")
	public UserResponseDTO save(@RequestBody UserDTO userDto) {
		return userService.save(userDto);
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestParam(required = true) Long id) {
		userService.delete(id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
		UserResponseDTO user = authenticationService.authenticate(userLoginDTO);
		
		if (user != null) return ResponseEntity.ok(user);

		return ResponseEntity.status(401).body("Informations incorrectes");
	}
}
