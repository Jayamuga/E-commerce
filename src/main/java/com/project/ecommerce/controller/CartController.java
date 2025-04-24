package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            model.addAttribute("cartEmpty", true);
            model.addAttribute("cart", new ArrayList<>()); // Provide an empty list
        } else {
            model.addAttribute("cart", cart);
            model.addAttribute("cartEmpty", false);
        }

        return "cart"; // Redirect to cart.html
    }
}
