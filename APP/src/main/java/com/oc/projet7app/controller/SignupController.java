package com.oc.projet7app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.projet7app.model.User;
import com.oc.projet7app.model.dto.UserDTO;
import com.oc.projet7app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String signupForm(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		
		return "signup";
	}
	
	@PostMapping
	public String signupSubmit(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}
		
		try {			
			User user = userService.registerUser(userDTO).block();
			
			request.getSession().setAttribute("user", user);
			
			return "redirect:/";
		} catch (Exception e) {
			model.addAttribute("error", "Une erreur est survenue lors de l'inscription");
			
			return "signup";
		}
	}
}
