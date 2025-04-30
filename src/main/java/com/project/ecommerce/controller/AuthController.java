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


	
	
	  @PostMapping("/api/login")
	  
	  @ResponseBody public ResponseEntity<?> apiLogin(@RequestBody Map<String,
	  String> payload, HttpSession session) { String email = payload.get("email");
	  String password = payload.get("password");
	  
	  boolean authenticated = authService.login(email, password); if
	  (authenticated) { User user = authService.getUserByEmail(email);
	  session.setAttribute("user", user); session.setAttribute("userId",
	  user.getId()); return ResponseEntity.ok("Login successful"); } else { return
	  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); }
	  }
	 
	 

	@GetMapping("/test")
	@ResponseBody
	public String testInsert() {
		User user = new User("TestUser", "test@user.com", "pass123");
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "Inserted test user.";
	}
}
