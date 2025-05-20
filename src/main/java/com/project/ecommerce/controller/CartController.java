package com.project.ecommerce.controller;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.UserRepository;

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
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }

        cartRepository.findByUserIdAndProductId(userId, productId)
            .ifPresent(cartRepository::delete);

        return "redirect:/cart";
    }
    
    
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }

        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        List<Product> cartProducts = cartItems.stream()
                .map(item -> productRepository.findById(item.getProductId()).orElse(null))
                .filter(product -> product != null)
                .toList();

        double total = cartProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        for (Product product : cartProducts) {
            System.out.println("Product: " + product.getName() + " | Price: " + product.getPrice());
        }

        
        model.addAttribute("cart", cartProducts);
        model.addAttribute("total", total);

        return "checkout";
    }


    @PostMapping("/place-order")
    public String placeOrder(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";

        // Fetch user from DB
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/auth/login";

        // Fetch cart items
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        // Convert to product list
        List<Product> products = cartItems.stream()
            .map(item -> productRepository.findById(item.getProductId()).orElse(null))
            .filter(p -> p != null)
            .toList();

        if (products.isEmpty()) return "redirect:/cart?error=empty";

        // Create order
        Order order = new Order(user, products);
        orderRepository.save(order);

        // Clear cart
        cartRepository.deleteAll(cartItems);

        return "redirect:/products?order=success";
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
