package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    // ✅ Constructor Injection
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Show Products page
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("newProduct", new Product());
        return "products"; 
    }

    // ✅ Add new Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productRepository.save(product);
        return "redirect:/products"; 
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
