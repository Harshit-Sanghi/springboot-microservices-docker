package com.example.orderservice.controller;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderController(OrderRepository repo, UserClient userClient, ProductClient productClient) {
        this.repo = repo;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    @GetMapping
    public List<Order> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Order order) {
        //  Verify user exists
        try {
            userClient.getUserById(order.getUserId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found or user-service unreachable");
        }

        //  Verify product exists and reserve stock
        try {
            productClient.getProductById(order.getProductId());
            String res = productClient.reserve(order.getProductId(), order.getQuantity() == null ? 0 : order.getQuantity());
            if (!"reserved".equalsIgnoreCase(res)) {
                return ResponseEntity.badRequest().body("Failed to reserve product stock");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product not found / insufficient stock / product-service unreachable");
        }

        //  Save order
        order.setStatus(OrderStatus.CREATED);
        Order saved = repo.save(order);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        return repo.findById(id).map(ex -> {
            ex.setProductId(order.getProductId());
            ex.setQuantity(order.getQuantity());
            ex.setUserId(order.getUserId());
    
            return ResponseEntity.ok(repo.save(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.noContent().build(); }
        return ResponseEntity.notFound().build();
    }
}
