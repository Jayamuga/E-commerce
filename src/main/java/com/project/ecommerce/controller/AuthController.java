package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        if (authService.checkUserExists(user.getEmail())) {
            return "redirect:/login?message=exists";
        } else {
            authService.registerUser(user);
            return "redirect:/products";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {
        System.out.println("📥 Received login request for email: " + email);
        boolean authenticated = authService.login(email, password);
        if (authenticated) {
            session.setAttribute("user", email);
            System.out.println("✅ Auth success. Redirecting to /products.");
            return "redirect:/products";
        } else {
            System.out.println("❌ Auth failed. Redirecting to /login.");
            return "redirect:/login?error=true";
        }
    }




}
