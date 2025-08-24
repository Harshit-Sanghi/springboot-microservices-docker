package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        if (p.getStock() == null) p.setStock(0);
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product p) {
        return repo.findById(id).map(ex -> {
            ex.setName(p.getName());
            ex.setDescription(p.getDescription());
            ex.setPrice(p.getPrice());
            ex.setStock(p.getStock());
            return ResponseEntity.ok(repo.save(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.noContent().build(); }
        return ResponseEntity.notFound().build();
    }

    
    @PostMapping("/{id}/reserve")
    public ResponseEntity<String> reserve(@PathVariable String id, @RequestParam("qty") int qty) {
        return repo.findById(id).map(p -> {
            if (qty <= 0) return ResponseEntity.badRequest().body("qty must be > 0");
            if (p.getStock() == null) p.setStock(0);
            if (p.getStock() < qty) return ResponseEntity.badRequest().body("Insufficient stock");
            p.setStock(p.getStock() - qty);
            repo.save(p);
            return ResponseEntity.ok("reserved");
        }).orElse(ResponseEntity.notFound().build());
    }
}
