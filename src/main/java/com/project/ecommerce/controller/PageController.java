package com.project.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

   

    @GetMapping("/")
    public String homePage() {
        return "index"; // Loads index.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // will load templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Loads register.html
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout"; // Loads checkout.html
    }

    @GetMapping("/products")
    public String productsPage(HttpSession session) {
        System.out.println("📦 Entered /products route");
        Object user = session.getAttribute("user");
        if (user == null) {
            System.out.println("⚠️ No user in session. Redirecting to login.");
            return "redirect:/login";
        }
        System.out.println("✅ User found: " + user.toString());
        return "products"; // products.html under /templates
    }

}
