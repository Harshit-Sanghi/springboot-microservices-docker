package com.example.orderservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}
