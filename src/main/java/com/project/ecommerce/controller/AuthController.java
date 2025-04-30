package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final UserRepository userRepository;

	public AuthController(AuthService authService, UserRepository userRepository) {
		this.authService = authService;
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	
	  
	@PostMapping("/register")
	public String register(@ModelAttribute User user, HttpSession session) {
	    if (authService.checkUserExists(user.getEmail())) {
	        return "redirect:/auth/login?message=exists";
	    }

	    if (user.getRole() == null) {
	        user.setRole("ROLE_USER"); // default role
	    }

	    authService.registerUser(user);
	    return "redirect:/login?registered=true";
	}


	
	@PostMapping("/login")
    public String login() {
        // Authentication is fully handled by Spring Security.
        return "redirect:/products";
    }
	  
	 
	
}
