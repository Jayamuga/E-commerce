package com.project.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.project.ecommerce.service.ProductService;
import org.springframework.ui.Model;

@Controller
public class PageController {

	private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String homePage() {
        return "index"; // Loads index.html
    }

    @GetMapping("/login")
    public String login() {
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

	
	/*
	 * @GetMapping("/products") public String productsPage(Model model) {
	 * model.addAttribute("products", productService.getAllProducts()); return
	 * "products"; // this should match your template name: products.html }
	 */
	 

}
