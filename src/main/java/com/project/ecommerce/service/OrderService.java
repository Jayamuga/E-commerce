package com.project.ecommerce.service;

import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order placeOrder(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Product> products = productRepository.findAllById(productIds);
        Order order = new Order(user, products);
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
