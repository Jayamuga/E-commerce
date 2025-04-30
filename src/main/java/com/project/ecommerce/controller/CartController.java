package com.project.ecommerce.controller;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

	/* @PreAuthorize("hasAuthority('USER')") */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/auth/login"; // If not logged in
        }

        Optional<CartItem> existingOpt = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingOpt.isPresent()) {
            CartItem existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepository.save(existing);
        } else {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            cartRepository.save(item);
        }

        return "redirect:/cart"; // After adding, go to cart page
    }

    

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }

        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        List<Product> cartProducts = cartItems.stream()
                .map(item -> productRepository.findById(item.getProductId()).orElse(null))
                .filter(product -> product != null)
                .toList();

        model.addAttribute("cart", cartProducts);
        model.addAttribute("cartEmpty", cartProducts.isEmpty());
        return "cart";
    }
}
