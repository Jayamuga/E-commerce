package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.service.AuthService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication; // ✅ Correct import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    private final ProductRepository productRepository;
    private final AuthService authService;
    

    public AdminController(ProductRepository productRepository, AuthService authService) {
        this.productRepository = productRepository;
        this.authService = authService; 
    }

    @GetMapping("/login-success")
    public String loginSuccess(Authentication auth, HttpSession session) {
        String email = auth.getName();
        User user = authService.getUserByEmail(email);
        
        session.setAttribute("userId", user.getId()); // ✅ store userId

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/products";
        }
    }


    @PostMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin-dashboard";
    }
}
