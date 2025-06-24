package com.oc.projet7app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@GetMapping
	public String profile(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute("user") != null) {
			response.setHeader("pragma", "no-cache");              
			response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");             
			response.setHeader("Expires", "0");
			
			return "profile";
		}
		
		return "redirect:/login";
	}
}
