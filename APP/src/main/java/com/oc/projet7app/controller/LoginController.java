package com.oc.projet7app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.projet7app.exception.AuthenticationException;
import com.oc.projet7app.model.User;
import com.oc.projet7app.model.dto.UserLoginDTO;
import com.oc.projet7app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String loginForm(Model model) {
		model.addAttribute("userLoginDTO", new UserLoginDTO());
		
		return "login";
	}
	
	@PostMapping
	public String loginSubmit(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO, HttpServletRequest request, Model model) {
		try {
	        User user = userService.authenticateUser(userLoginDTO).block();

	        request.getSession().setAttribute("user", user);
	        
	        return "redirect:/";
	    } catch (AuthenticationException error) {
	        model.addAttribute("error", error.getMessage());
	        
	        return "login";
	    } catch (Exception e) {
	        model.addAttribute("error", "Une erreur est survenue lors de l'authentification");
	        
	        return "login";
	    }
	}
}
