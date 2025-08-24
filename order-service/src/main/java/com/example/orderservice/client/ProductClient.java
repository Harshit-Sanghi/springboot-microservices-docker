package com.example.orderservice.client;

import com.example.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${PRODUCT_SERVICE_URL:http://localhost:8083}")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable("id") String id);

    @PostMapping("/products/{id}/reserve")
    String reserve(@PathVariable("id") String id, @RequestParam("qty") int qty);
}
