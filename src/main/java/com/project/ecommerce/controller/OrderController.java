package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.*;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/{userId}")
    public Order placeOrder(@PathVariable Long userId, @RequestBody List<Long> productIds) { // ✅ Change to List<Long>
        return orderService.placeOrder(userId, productIds);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully!";
    }
}
