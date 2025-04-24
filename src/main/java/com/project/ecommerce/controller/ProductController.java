package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor-based dependency injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ GET all products (used by JS in products.html)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(products);
    }

    // ✅ GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null
                ? ResponseEntity.ok(product)
                : ResponseEntity.notFound().build();
    }

    // ✅ POST a new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    // ✅ PUT (update) a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    // ✅ DELETE a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

